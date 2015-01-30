/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.action;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;

import com.camel.realtimelog.FileIdentification;
import com.camel.realtimelog.RealtimeLogReader;
import com.camel.realtimelog.domain.Logmark;
import com.camel.realtimelog.domain.LogmarkFile;
import com.camel.utils.FileUtils;

/**
 * 
 * @author dengqb
 * @date 2015年1月27日
 */
public class JobBreakValidateAction implements IBaseAction {
    private final static Logger logger = Logger.getLogger(JobBreakValidateAction.class);
    
    /**
     * 判断当前JOB是否需要中断，等待下一次运行
     * 当LOG日志文件OS ID发生变化时，表示日志文件已经被新文件替换。需要重新读取
     * @param logmark
     * @return boolean true:需要中断，false:可以继续运行
     */
    public boolean validate(final Logmark logmark){
        // 获取当前文件的唯一标识
        String currentFileId = FileIdentification.getFileIdentity(logmark.getLogFileAbsolutePath()).trim();
        if (!currentFileId.equals(logmark.getLastFileOSId())) {
            //如果log文件os id不等于现有文件os id。表示需要重新开始读取日志
            logmark.setLastReadSize(0);
            
            if (StringUtils.isEmpty(logmark.getLastFileOSId())){
                //原logmark不存在os id，表示第一次读取
                logmark.setLastFileOSId(currentFileId);
                logger.debug(logmark.getLogFileAbsolutePath() + " first time run this app.");
                return false;
            } else {
                //os id存在，但是不相等，表示日志文件已经产生新文件
                logmark.setLastFileOSId(currentFileId);
                //更新标记文件mark file
                
                writeLogMarkPropertes(logmark);
                logger.debug(LogmarkFile.getFileAbsolutePath()
                        + " log file has backed up, break current operation. waiting 10 secs to rerun");
                return true;
            }
        }
        return false;
    }
    
    private static void writeLogMarkPropertes (final Logmark logmark){
        Properties markProps = new Properties();
        
        markProps.setProperty(LogmarkFile.KEY_LASTTIME_FILE_SIZE + logmark.getFileId(), String.valueOf(logmark.getLastReadSize()));
        markProps.setProperty(LogmarkFile.KEY_LASTTIME_FILE_ID + logmark.getFileId(), logmark.getLastFileOSId() == null ? "" : logmark.getLastFileOSId());
        FileUtils.writeProperties(LogmarkFile.getFileAbsolutePath(), markProps);
    }
}
