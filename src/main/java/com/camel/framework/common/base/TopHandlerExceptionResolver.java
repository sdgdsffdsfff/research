/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.framework.common.base;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;

/**
 * <pre>
 * spring MVC框架的exception处理器，没有自定义catch并处理的，都会在这里处理
 * </pre>
 * 
 * @author camel.deng
 * @date 2013年11月11日
 */
public class TopHandlerExceptionResolver implements HandlerExceptionResolver {
    /**
     * logger
     */
    private Logger logger = Logger.getLogger(TopHandlerExceptionResolver.class);

    @Autowired
    private ResourceBundleMessageSource messageSource;

    /**
     * 异常处理
     * 
     * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, java.lang.Object,
     *      java.lang.Exception)
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof MaxUploadSizeExceededException) {
            // SpringMVC在超出上传文件限制时，会抛出org.springframework.web.multipart.MaxUploadSizeExceededException
            // 该异常是SpringMVC在检查上传的文件信息时抛出来的，而且此时还没有进入到Controller方法中
            logger.error("Attachment too large", ex);

        } else if (ex instanceof RuntimeException) {
            ModelAndView view = new ModelAndView("errors/error", "ctx", request.getContextPath());
            view.addObject("code", "runtime");
            view.addObject("msg", "runtime error");
            return view;
        } else {
            logger.error("System error", ex);
        }

        return new ModelAndView("errors/500", "ctx", request.getContextPath());
    }

    /**
     * 获取异常信息
     * @param errCode 异常代码
     * @param args 参数
     * @return 异常信息 
     */
    protected String getI18nExceptionMsg(String errCode, Object[] args) {

        String language = ConfigParamMap.getValue("language");
        Locale locale = Locale.SIMPLIFIED_CHINESE;

//        String[] tmp = StringUtils.split(language, "_");
//        if (tmp != null && tmp.length == 2) {
//            locale = new Locale(tmp[0], tmp[1]);
//
//        }

        return messageSource.getMessage(errCode, args, locale);
    }

}
