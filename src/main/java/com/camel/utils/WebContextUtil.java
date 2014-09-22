/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 获取spring上下文，可以通过bean名称等方式获取spring注入的bean
 * 
 * @author camdel.deng 2011-12-14
 * @since v1.0.0
 */
public final class WebContextUtil {

    /**
     * 构造函数
     */
    private WebContextUtil() {

    }

    /**
     * web 上下文
     */
    private static WebApplicationContext wac;

    /**
     * 获取当前的request对象
     * 
     * @return 当前request
     */
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前web上下文
     * 
     * @return 当前web context
     */
    public static WebApplicationContext getWebApplicationContext() {
        if (wac == null) {
            HttpServletRequest request = getCurrentRequest();
            wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
        }
        return wac;
    }

    /**
     * 根据sevlet上下文获取当前web上下文
     * 
     * @param event servlet上下文
     * @return 当前web上下文
     */
    public static WebApplicationContext getWebApplicationContext(ServletContextEvent event) {
        wac = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        return wac;
    }

    /**
     * 根据bean名称获取bean
     * 
     * @param beanName bean的名称
     * @return bean对象
     */
    public static Object getBean(String beanName) {
        return getWebApplicationContext().getBean(beanName);
    }

    public static WebApplicationContext getWac() {
        return wac;
    }

    public static void setWac(WebApplicationContext wac) {
        WebContextUtil.wac = wac;
    }
}
