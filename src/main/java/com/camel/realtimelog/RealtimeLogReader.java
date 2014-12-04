package com.camel.realtimelog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.camel.utils.FileUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 实时日志读取
 * 
 * @author dengqb
 * @date 2014年11月3日
 */
public class RealtimeLogReader {
    private static final Logger logger = Logger.getLogger(RealtimeLogReader.class);
    
    private Properties config;
    /**
     * 日志处理标记存储文件
     */
    private static String markFilePath = "/config/logmark";
    /**
     * 上次读取的文件位置信息key值前缀
     */
    private final String KEY_LASTTIME_FILE_SIZE = "last.file.size.";
    /**
     * 上次读取的文件的唯一标识id的key值前缀
     */
    private final String KEY_LASTTIME_FILE_ID = "lats.file.id.";
    /**
     * 配置文件
     */
    private static String configFilePath = "/config/config.properties";
    //private static final String configFilePath = "C:\\Users\\dengqb\\Desktop\\reallog\\config\\config.properties";
    
    private static String currentAppPath = "";
    
    /**
     * error 日志正则表达式
     */
    //String regex = "^20[1-9][0-9]-(0?[1-9]|1[0-2])-((0?[1-9])|(1|2[0-9])|30|31)\\s((0?[0-9])|(1[0-9])|(2[0-4])):((0?[0-9])|([1-6][0-9])):((0?[0-9])|([1-6][0-9]))(,\\d*)?\\sERROR\\s.*";
    String logHeaderRegex = "^20[1-9][0-9]-([0-1][0-9])-([0-3][0-9])\\s([0-2][0-9]):([0-6][0-9]):([0-6][0-9])(,\\d*)?\\s(ERROR|INFO|DEBUG|WARN).*";
    String logLevelRegex = "(.*)\\s(ERROR|INFO|DEBUG|WARN)\\s(.*)";
    String logDateRegex = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})(.*)";
    
    private static Pattern logDatePattern;
    private static Pattern logLevelPattern;
    
    /**
     * 考虑SimpleDateFormat在多线程环境中的安全问题，使用ThreadLocal解决这个问题
     */
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();
    private static final String date_format = "yyyy-MM-dd HH:mm:ss";
    /**   
     * 实时输出错误日志信息
     * @param logFile 日志文件
     * @throws IOException
     */    
    public void realtimeErrorLog() throws IOException{
        //读取配置文件
        //runnable jar 模式
        if (currentAppPath.isEmpty()){
            currentAppPath = FileUtils.getFileAbsolutePath();
            configFilePath = currentAppPath + configFilePath;
            markFilePath = currentAppPath + markFilePath;
            logger.debug("configFilePath = "+ configFilePath + "## markFilePath = " + markFilePath);
        }
        config = FileUtils.readProperties(configFilePath);
        List<LogFileConfiguration> logFileConfigs = new ArrayList<LogFileConfiguration>();
        Iterator<Object> it = config.keySet().iterator();
        while(it.hasNext()){
            String key = (String) it.next();
            LogFileConfiguration cfg = new LogFileConfiguration();
            if (key.startsWith("log.files.path.")){
                String id = key.substring(key.lastIndexOf(".")+1);
                cfg.setId(id);
                cfg.setFilePath(config.getProperty(key));
                cfg.setAppName(config.getProperty("log.files.app."+id));
                logFileConfigs.add(cfg);
            }
        }
        
        //数据库访问对象
        final IPersistenceAdapter mongo = new PersistenceMongoAccessor(config.getProperty("db.ip"), config.getProperty("db.name"), config.getProperty("db.coll.name"));
        
        //启动一个线程每10秒钟读取新增的日志信息     
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(logFileConfigs.size());     
        for (LogFileConfiguration cfg : logFileConfigs){
            sheduleJob(exec, cfg, mongo);
        }
    } 
    
    private void sheduleJob(ScheduledExecutorService exec, final LogFileConfiguration cfg,final IPersistenceAdapter mongo){
      //指定文件可读可写     
        try {
            final RandomAccessFile randomFile = new RandomAccessFile(cfg.getFilePath(),"rw");
            final Map<String,DBObject> errorMap = new HashMap<String,DBObject>();
            exec.scheduleWithFixedDelay(new Runnable(){
                public void run() {
                    logger.debug("start of log collection");
                    try {
                        Properties markProps = new Properties();
                        if (FileUtils.isExist(markFilePath)){
                            markProps = FileUtils.readProperties(markFilePath);
                        }else{
                            FileUtils.createFile(markFilePath);
                        }
                        //有2种情况，lastTimeFileSize可以等于0
                        //1. 第一次读取log文件
                        //2. 应用停止后重启
                        if (cfg.getLastReadFileSize() == 0){
                            if (markProps != null){
                                String lastTimeFileSize = markProps.getProperty(KEY_LASTTIME_FILE_SIZE+cfg.getId());
                                if (lastTimeFileSize != null){
                                    cfg.setLastReadFileSize(Long.valueOf(markProps.getProperty(KEY_LASTTIME_FILE_SIZE+cfg.getId())));
                                    cfg.setLastReadFileId(markProps.getProperty(KEY_LASTTIME_FILE_ID+cfg.getId()).trim());
                                }
                            }
                        } 
                        
                        //获取当前文件的唯一标识
                        String currentFileId = FileIdentification.getFileIdentity(cfg.getFilePath()).trim();
                        //如果文件标识不一致，则文件读取位置从0开始
                        if (!currentFileId.equals(cfg.getLastReadFileId())){
                            cfg.setLastReadFileSize(0);
                            cfg.setLastReadFileId(currentFileId);
                        }
                        
                        if (randomFile.length() == cfg.getLastReadFileSize()){
                            //相等表示日志无变化，不需要处理
                            logger.debug(cfg.getFilePath() +" log file has not changed");
                            return;
                        }
                        
                        logDatePattern = Pattern.compile(logDateRegex);
                        logLevelPattern = Pattern.compile(logLevelRegex);
                        //获得变化部分的     
                        randomFile.seek(cfg.getLastReadFileSize());
                        String tmp = "";
                        StringBuilder errorKey = new StringBuilder();
                        StringBuilder errorValue = new StringBuilder();
                        while( (tmp = randomFile.readLine())!= null) {
                            if (tmp.matches(logHeaderRegex)){
                                if (errorKey.length() > 0){
                                    //保存上一个的error 堆栈信息
                                    DBObject logObj = geneLogObj(errorKey.toString(),errorValue.toString(),cfg.getAppName());
                                    
                                    errorMap.put(errorKey.toString(), logObj);
                                    //清空string builder
                                    errorKey.delete(0, errorKey.length());
                                    errorValue.delete(0, errorValue.length());
                                }
                                Matcher matcher = logLevelPattern.matcher(tmp);
                                if (matcher.matches()){
                                    if (matcher.group(2).equalsIgnoreCase("ERROR")){
                                        errorKey.append(tmp + randomFile.getFilePointer());
                                        errorValue.append(tmp + System.lineSeparator());
                                    }
                                }
                            }else{
                                if (errorKey.length() > 0){
                                    errorValue.append(tmp + System.lineSeparator());
                                }
                            }
                        }
                        if (errorKey.length() > 0){
                            //保存最后一个的error 堆栈信息
                            DBObject logObj = geneLogObj(errorKey.toString(),errorValue.toString(),cfg.getAppName());
                            errorMap.put(errorKey.toString(), logObj);
                        }
                        
                        logger.debug(cfg.getFilePath()+" has "+errorMap.size()+" errors. File size from "+ cfg.getLastReadFileSize() +" to "+randomFile.length() );
                        cfg.setLastReadFileSize(randomFile.length());
                        
                        try{
                            //读取完成后
                            //1.保存解析的log日志至mongodb存储
                            if (errorMap.size() > 0){
                                mongo.saveAll(new ArrayList<DBObject>(errorMap.values()));
                            }
                            //2.并且更新标记文件mark file
                            markProps.setProperty(KEY_LASTTIME_FILE_SIZE+cfg.getId(), String.valueOf(cfg.getLastReadFileSize()));
                            markProps.setProperty(KEY_LASTTIME_FILE_ID+cfg.getId(), cfg.getLastReadFileId());
                            FileUtils.writeProperties(markFilePath, markProps);
                        }catch (Exception e){
                            logger.error("save operation exception. errorMap size="+errorMap.size(), e);
                            e.printStackTrace();
                        }
                        
                        //保存数据后，需要做如下操作：
                        //1、Last read size设为0，需要重新从logmark文件中读取
                        cfg.setLastReadFileSize(0);
                        //2、清空errorMap
                        errorMap.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    logger.debug("end of log collection");
                }     
            }, 0, 10, TimeUnit.SECONDS);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }
    
    /**
     * 生成mongodb存储对象
     * @param header log header
     * @param stack  log 堆栈信息
     * @param appName log所属应用名称
     * @return mongodb存储对象
     */
    private DBObject geneLogObj(String header, String stack, String appName){
        DBObject logObj = new BasicDBObject();
        logObj.put("header", header);
        logObj.put("stack", stack);
        logObj.put("app", appName);
        Matcher matcher = logDatePattern.matcher(header);
        if (matcher.matches()){
            String logDate = matcher.group(1);
            try {
                logObj.put("logDate", getDateFormat().parse(logDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        matcher = logLevelPattern.matcher(header);
        if (matcher.matches()){
            logObj.put("logLevel", matcher.group(2));
        }
        return logObj;
    }
    
    /**
     * 使用threadlocal创建线程安全的SimpleDateFormat
     * @return
     */
    private static DateFormat getDateFormat(){
        DateFormat df = threadLocal.get();
        if(df == null){
            df = new SimpleDateFormat(date_format);
            threadLocal.set(df);
        }
        return df;
    }
    
    public static void main(String[] args) throws Exception {
        RealtimeLogReader view = new RealtimeLogReader();
        view.realtimeErrorLog();
    }     
}
