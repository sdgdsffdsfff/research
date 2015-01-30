package com.camel.realtimelog.domain;

/**
 * Config文件中标记需要监控的日志文件
 * 
 * @author dengqb
 * @date 2015年1月27日
 */
public class LogFileConfig {
    /**
     * log.files.path.1，log.files.path.2中的后缀：1或者2
     */
    private String id;
    /**
     * log文件路径，绝对路径
     */
    private String filePath;
    /**
     * log文件app名称
     */
    private String appName;
    
    /**
     * 最后读取文件的id
     */
    private String lastReadFileId;
    /**
     * 最后读取文件的位置
     */
    private long lastReadFileSize;
    
    public String getFilePath() {
        return filePath;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAppName() {
        return appName;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public String getLastReadFileId() {
        return lastReadFileId;
    }
    public void setLastReadFileId(String lastReadFileId) {
        this.lastReadFileId = lastReadFileId;
    }
    public long getLastReadFileSize() {
        return lastReadFileSize;
    }
    public void setLastReadFileSize(long lastReadFileSize) {
        this.lastReadFileSize = lastReadFileSize;
    }
}
