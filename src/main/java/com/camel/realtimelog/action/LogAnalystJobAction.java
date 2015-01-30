package com.camel.realtimelog.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.camel.realtimelog.domain.Logmark;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class LogAnalystJobAction implements IBaseAction {
    private final Map<String, DBObject> errorMap = new HashMap<String, DBObject>();
    /**
     * error 日志正则表达式
     */
    // String regex =
    // "^20[1-9][0-9]-(0?[1-9]|1[0-2])-((0?[1-9])|(1|2[0-9])|30|31)\\s((0?[0-9])|(1[0-9])|(2[0-4])):((0?[0-9])|([1-6][0-9])):((0?[0-9])|([1-6][0-9]))(,\\d*)?\\sERROR\\s.*";
    private String logHeaderRegex = "^20[1-9][0-9]-([0-1][0-9])-([0-3][0-9])\\s([0-2][0-9]):([0-6][0-9]):([0-6][0-9])(,\\d*)?\\s(ERROR|INFO|DEBUG|WARN).*";
    private String logLevelRegex = "(.*)\\s(ERROR|INFO|DEBUG|WARN)\\s(.*)";
    private String logDateRegex = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})(.*)";

    private static Pattern logDatePattern;
    private static Pattern logLevelPattern;

    /**
     * 考虑SimpleDateFormat在多线程环境中的安全问题，使用ThreadLocal解决这个问题
     */
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();
    private static final String date_format = "yyyy-MM-dd HH:mm:ss";
    
    public Map<String, DBObject> analyse(InputStream inputStream, Logmark logmark) {
        if (inputStream == null){
            return null;
        }
        logDatePattern = Pattern.compile(logDateRegex);
        logLevelPattern = Pattern.compile(logLevelRegex);
        
        StringBuilder errorKey = new StringBuilder();
        StringBuilder errorValue = new StringBuilder();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        
        errorMap.clear();
        String lineStr = "";
        try {
            int count = 0;
            while ((lineStr = br.readLine()) != null){
                count ++;
                if (lineStr.matches(logHeaderRegex)) {
                    //如果匹配日志头信息，表示新的堆栈信息产生，
                    //如果存在日志头，表示旧的日志堆栈读取已经完成。
                    if (errorKey.length() > 0) {
                        DBObject logObj = geneLogObj(errorKey.toString(), errorValue.toString(), logmark.getAppName());
                        //完整堆栈信息存入map
                        errorMap.put(errorKey.toString(), logObj);
                        
                        errorKey.delete(0, errorKey.length());
                        errorValue.delete(0, errorValue.length());
                    }
                    Matcher matcher = logLevelPattern.matcher(lineStr);
                    if ((matcher.matches()) && (matcher.group(2).equalsIgnoreCase("ERROR"))) {
                        errorKey.append(lineStr + " line:"+count);
                        errorValue.append(lineStr + System.lineSeparator());
                    }
                } else if (errorKey.length() > 0) {
                    errorValue.append(lineStr + System.lineSeparator());
                }
            }
            
            if (errorKey.length() > 0) {
                // 保存最后一个的error 堆栈信息
                DBObject logObj = geneLogObj(errorKey.toString(), errorValue.toString(), logmark.getAppName());
                errorMap.put(errorKey.toString(), logObj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorMap;
    }
    
    /**
     * 生成mongodb存储对象
     * 
     * @param header log header
     * @param stack log 堆栈信息
     * @param appName log所属应用名称
     * @return mongodb存储对象
     */
    private DBObject geneLogObj(String header, String stack, String appName) {
        DBObject logObj = new BasicDBObject();
        logObj.put("header", header);
        logObj.put("stack", stack);
        logObj.put("app", appName);
        Matcher matcher = logDatePattern.matcher(header);
        if (matcher.matches()) {
            String logDate = matcher.group(1);
            try {
                logObj.put("logDate", getDateFormat().parse(logDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        matcher = logLevelPattern.matcher(header);
        if (matcher.matches()) {
            logObj.put("logLevel", matcher.group(2));
        }
        return logObj;
    }
    
    /**
     * 使用threadlocal创建线程安全的SimpleDateFormat
     * 
     * @return
     */
    private static DateFormat getDateFormat() {
        DateFormat df = threadLocal.get();
        if (df == null) {
            df = new SimpleDateFormat(date_format);
            threadLocal.set(df);
        }
        return df;
    }
}
