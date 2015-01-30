/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.action;

import java.util.Properties;

import com.camel.realtimelog.domain.Configuration;
import com.camel.realtimelog.domain.LogmarkFile;
import com.camel.utils.FileUtils;

/**
 * 读取logmark文件
 * @author dengqb
 * @date 2015年1月21日
 */
public class ReadLogmarkFileAction implements IBaseAction {
    
    public void readLogmarkFile(final Configuration config, final LogmarkFile logmarkFile){
        String filePath = logmarkFile.getFileAbsolutePath();
        Properties markProps = new Properties();
        if (!FileUtils.isExist(filePath)) {
            FileUtils.createFile(filePath);
        } 
        markProps = FileUtils.readProperties(filePath);
        logmarkFile.convertLogmark(config, markProps);
    }
}
