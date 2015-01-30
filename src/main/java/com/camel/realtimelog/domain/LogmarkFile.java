/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.camel.utils.FileUtils;

/**
 * log mark file business object。
 * 用于传递logmarkFile object
 * @author dengqb
 * @date 2015年1月21日
 */
public class LogmarkFile {
    
    /**
     * 上次读取的文件位置信息key值前缀
     */
    public final static String KEY_LASTTIME_FILE_SIZE = "last.file.size.";
    /**
     * 上次读取的文件的唯一标识id的key值前缀
     */
    public final static String KEY_LASTTIME_FILE_ID = "lats.file.id.";
    
    /**
     * log mark文件相对路径
     */
    private final static String RELATIVE_PATH = "/config/logmark";
    
    private static String fileAbsolutePath = "";
    
    private List<Logmark> logmarks;

    public static String getFileAbsolutePath() {
        setFileAbsolutePath(App.getAppAbsolutePath() + RELATIVE_PATH);
        return fileAbsolutePath;
    }

    public static void setFileAbsolutePath(String fileAbsolutePath) {
        LogmarkFile.fileAbsolutePath = fileAbsolutePath;
    }

    public List<Logmark> getLogmarks() {
        if (logmarks == null){
            logmarks = new ArrayList<Logmark>();
        }
        return logmarks;
    }

    public void setLogmarks(List<Logmark> logmarks) {
        this.logmarks = logmarks;
    }
    
    public void convertLogmark(final Configuration config, final Properties markProps){
        if (markProps != null){
            List<LogFileConfig> logFiles = config.getLogFileConfigs();
            for (LogFileConfig logFile: logFiles){
                Logmark logmark = new Logmark(logFile.getId());
                logmark.setLogFileAbsolutePath(logFile.getFilePath());
                logmark.setAppName(logFile.getAppName());
                String sizeStr = markProps.getProperty(KEY_LASTTIME_FILE_SIZE+logmark.getFileId());
                if (sizeStr == null){
                    logmark.setLastReadSize(0L);
                }else{
                    logmark.setLastReadSize(Long.valueOf(sizeStr));
                }
                
                String fileOSId = markProps.getProperty(KEY_LASTTIME_FILE_ID+logmark.getFileId());
                if (fileOSId == null){
                    fileOSId = "";
                }
                logmark.setLastFileOSId(fileOSId);
                getLogmarks().add(logmark);
            }
        }
    }
}
