/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.utils;

import java.util.Properties;

import com.camel.realtimelog.LogFileConfiguration;
import com.camel.realtimelog.domain.Logmark;
import com.camel.realtimelog.domain.LogmarkFile;
import com.camel.utils.FileUtils;

/**
 * 
 * @author dengqb
 * @date 2015年1月27日
 */
public class WriteLogmarkUtils {
    
    /**
     * 标记更新logmark文件
     * @param cfg
     * @param markProps
     */
    public void writeLogmarkFile(final Logmark logmark){
        Properties markProps = new Properties();
        
        markProps.setProperty(LogmarkFile.KEY_LASTTIME_FILE_SIZE + logmark.getFileId(), String.valueOf(logmark.getLastReadSize()));
        markProps.setProperty(LogmarkFile.KEY_LASTTIME_FILE_ID + logmark.getFileId(), logmark.getLastFileOSId() == null ? "" : logmark.getLastFileOSId());
        FileUtils.writeProperties(LogmarkFile.getFileAbsolutePath(), markProps);
    }
}   
