/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.camel.realtimelog.domain.Configuration;
import com.camel.realtimelog.domain.LogFileConfig;
import com.camel.realtimelog.domain.MongdbConfig;
import com.camel.utils.FileUtils;

/**
 * 读取配置文件
 * @author dengqb
 * @date 2015年1月21日
 */
public class ReadConfigAction implements IBaseAction {
    /**
     * 配置properties
     */
    private Properties configProps;
    
    /**
     * 初始载入config配置文件内容
     * @param config
     */
    private void loadConfig(final Configuration config){
        configProps = FileUtils.readProperties(config.getConfigAbolutePath());
        
        List<LogFileConfig> logFileConfigs = new ArrayList<LogFileConfig>();
        Iterator<Object> it = configProps.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            LogFileConfig logcfg = new LogFileConfig();
            if (key.startsWith("log.files.path.")) {
                String id = key.substring(key.lastIndexOf(".") + 1);
                logcfg.setId(id);
                logcfg.setFilePath(configProps.getProperty(key));
                logcfg.setAppName(configProps.getProperty("log.files.app." + id));
                logFileConfigs.add(logcfg);
            }
        }
        config.setLogFileConfigs(logFileConfigs);
        
        MongdbConfig mongdbConfig = new MongdbConfig();
        mongdbConfig.setServerAddress(configProps.getProperty("db.ip"));
        mongdbConfig.setDbName(configProps.getProperty("db.name"));
        mongdbConfig.setCollectionName(configProps.getProperty("db.coll.name"));
        mongdbConfig.setUsername(configProps.getProperty("db.username"));
        mongdbConfig.setPassword(configProps.getProperty("db.password"));
        
        config.setMongdbConfig(mongdbConfig);
    }
    
    /**
     * 获取配置信息
     * @param config
     */
    public void ReadConfig(final Configuration config){
        if ((config.getLogFileConfigs() == null ||config.getLogFileConfigs().size() == 0)
                || config.getMongdbConfig() == null){
            loadConfig(config);
        }
    }
    
    /**
     * 重新读取配置信息
     * @param config
     */
    public void ReloadConfig(final Configuration config){
        loadConfig(config);
    }
}
