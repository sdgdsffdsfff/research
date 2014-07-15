/*
 * Copyright (c) 2014, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.framework.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;

/**
 * @DESC:引用静态资源路径的自定义标签
 * @author FANGYF
 * @date 2013-5-8
 */
public class StaticTag extends TagSupport {

    private static final long serialVersionUID = 3247511290922508229L;

    /**
     * 记录日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(StaticTag.class);

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        this.pageContext.getServletConfig();
        // 获得jsp输出对象JspWriter
        JspWriter out = this.pageContext.getOut();

        // 从environmentConfig.xml里获取静态资源文件访问地址的value
        String resourcesUrl = null;
        if (null != ConfigParamMap.getConfigParamMap()) {
            ConfigParamMap.getValue(IEnvironmentConfigBasic.RESOURCES_URL);
        }

        // 如果配置文件为null，默认获取当前环境URL
        if (null == resourcesUrl) {
            HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
            resourcesUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
        }
        
        if (StringUtils.isNotBlank(resourcesUrl)) {
            try {
                // 如果配置文件指定了resourcesUrl的值，则在原有路径前加上静态资源文件访问地址
                out.print(resourcesUrl);
            } catch (IOException e) {
                logger.error("Could not print out value '" + resourcesUrl, e);
            }
        } else {
            logger.error("resourcesUrl is null,so static tag is invalid");
        }

        return SKIP_BODY;
    }
}
