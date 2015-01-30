/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.domain;

import java.util.List;

import com.camel.utils.FileUtils;

/**
 * 配置类
 * @author dengqb
 * @date 2015年1月21日
 */
public class Configuration {
    /**
     * 配置文件相对路径
     */
    private final String RELATIVE_PATH = "/config/config.properties";
    /**
     * 配置文件绝对路径
     */
    private String configAbolutePath = "";
    
    /**
     * 需要监控的文件列表配置
     */
    private List<LogFileConfig> logFileConfigs;
    /**
     * 数据库配置
     */
    private MongdbConfig mongdbConfig;

    public String getConfigAbolutePath() {
        setConfigAbolutePath(App.getAppAbsolutePath()+RELATIVE_PATH);
        return configAbolutePath;
    }

    public void setConfigAbolutePath(String configAbolutePath) {
        this.configAbolutePath = configAbolutePath;
    }

    public List<LogFileConfig> getLogFileConfigs() {
        return logFileConfigs;
    }

    public void setLogFileConfigs(List<LogFileConfig> logFileConfigs) {
        this.logFileConfigs = logFileConfigs;
    }

    public MongdbConfig getMongdbConfig() {
        return mongdbConfig;
    }

    public void setMongdbConfig(MongdbConfig mongdbConfig) {
        this.mongdbConfig = mongdbConfig;
    }

    
}
