/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.framework.listener;

import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.utils.AOPDynamicConfigurator.facade.EnvConfigFacade;

/**
 * <pre>
 * DESC：环境参数代理设置监听器
 * @author camel.deng
 * @2013-5-8
 * </pre>
 */
public class EnvConfigProxyListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // do nothing
    }

    /**
     * 监听器，负责读取并设置环境参数
     */
    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // 初始化环境配置系统，主要做2件事：
        // 1. 加载环境参数
        // 2. AOP拦截处理spring和log4j的占位符
        ResourceBundle rb = ResourceBundle.getBundle("envConfigSelector");
        String envConfigLocation = rb.getString("envConfigLocation");
        if (envConfigLocation == null || envConfigLocation.startsWith("${")) {
            envConfigLocation = null;
        }
        EnvConfigFacade.setConfigParamTable("cfg_config_param");
        EnvConfigFacade.initiation(envConfigLocation);
    }
}
