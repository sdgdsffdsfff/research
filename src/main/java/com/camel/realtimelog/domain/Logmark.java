/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.domain;


/**
 * log文件处理标记文件
 * @author dengqb
 * @date 2015年1月21日
 */
public class Logmark {
    /**
     * logmark记录对应的log日志文件绝对路径
     */
    private String logFileAbsolutePath;
    /**
     * logmark对应的的应用名称
     */
    private String appName;
    /**
     * log文件序列号，记录在config中
     */
    private String fileId;
    /**
     * 最后读取的文件position
     */
    private long lastReadSize;
    /**
     * 最后读取的文件唯一标识，标识与操作系统有关
     */
    private String lastFileOSId;
    
    public Logmark(String fileId){
        this.setFileId(fileId);
    }
    
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getLastFileOSId() {
        return lastFileOSId;
    }

    public void setLastFileOSId(String lastFileOSId) {
        this.lastFileOSId = lastFileOSId;
    }
    public long getLastReadSize() {
        return lastReadSize;
    }
    public void setLastReadSize(long lastReadSize) {
        this.lastReadSize = lastReadSize;
    }
    public String getLogFileAbsolutePath() {
        return logFileAbsolutePath;
    }
    public void setLogFileAbsolutePath(String logFileAbsolutePath) {
        this.logFileAbsolutePath = logFileAbsolutePath;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
