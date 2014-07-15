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
 * 引入Css文件的自定义标签
 * 
 * @author FANGYF
 * @date 2013-5-8
 */
public class CssLinkTag extends TagSupport {

    private static final long serialVersionUID = -7109212210750546011L;
    
    private static final Logger logger = LoggerFactory.getLogger(CssLinkTag.class);

    // link标签的type
    private String type;

    // link标签的rel
    private String rel;

    // 引入css的路径
    private String path;

    // 引用css的源文件,多个以逗号隔开
    private String href;

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        this.pageContext.getServletConfig();
        // 初始化参数
        initParam();
        // 解析标签并输出新标签
        parse();
        return SKIP_BODY;
    }

    /**
     * 根据配置解析标签并输出新的标签，一种是Coogle Minify模式,一种是一般的模式
     */
    private void parse() {
        StringBuffer sb = new StringBuffer();

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
                    + request.getContextPath() ;
        }
        /**
         * 根据从environmentConfig.xml配置的resources_merger，
         * 决定解析js和css的模式,Y使用google minify N不使用
         */
        String resourcesMerger = null;
        if (null != ConfigParamMap.getConfigParamMap()) {
            ConfigParamMap.getValue(IEnvironmentConfigBasic.RESOURCES_MERGER);
        }
        if (ITagBasic.USER_MINIFY_CODE.equals(resourcesMerger)) {
            // 将标签解析成 Minify格式的link标签再输出到页面
            parseToMinifyStyle(sb, resourcesUrl, out);
        } else {
            // 在指定path前追加上静态资源文件访问地址
            // 将标签解析成非 Minify格式的script标签再输出到页面
            parseToConventionalStyle(sb, resourcesUrl, out);
        }
    }

    /**
     * 将标签解析成 Minify格式的link标签再输出到页面
     * 
     * @param sb
     * @param resourcesUrl
     * @param out
     */
    private void parseToMinifyStyle(StringBuffer sb, String resourcesUrl, JspWriter out) {
        // 将原有的字符串清空
        sb.setLength(0);
        // 按照minify格式拼接link标签
        sb.append("<link type='").append(type).append("' rel='").append(rel).append("' href='").append(resourcesUrl)
                .append("min/?");

        if (StringUtils.isNotBlank(path)) {
            sb.append("b=").append(path).append("&");
        }

        sb.append("f=").append(href).append("'/>");

        try {
            // 输出到页面
            out.println(sb.toString());

        } catch (IOException e) {
            logger.error("Could not print out value '" + sb.toString(), e);
        }
    }

    /**
     * 将标签解析成非 Minify格式的link标签再输出到页面
     * 
     * @param sb
     * @param out
     */
    private void parseToConventionalStyle(StringBuffer sb, String resourcesUrl, JspWriter out) {

        // 将css源文件字符串以逗号拆分成数组
        String[] hrefArray = href.split(ITagBasic.SEPARATOR_COMMA);

        for (String hrefStr : hrefArray) {
            sb.setLength(0);
            sb.append("<link type='").append(type).append("' rel='").append(rel).append("' href='")
                    .append(resourcesUrl);

            if (StringUtils.isNotBlank(path)) {
                sb.append(path).append("/");
            }

            sb.append(hrefStr.trim()).append("'/>");

            try {
                // 输出到页面
                out.println(sb.toString());

            } catch (IOException e) {
                logger.error("Could not print out value '" + sb.toString(), e);
            }
        }

    }

    /**
     * 初始化link标签一些属性
     */
    private void initParam() {
        // 如果没有指定link的type则默认为"text/css"
        if (StringUtils.isBlank(type)) {
            type = ITagBasic.LINK_TYPE_TEXT_CSS;
        }

        // 如果没有指定link的rel则默认为"stylesheet"
        if (StringUtils.isBlank(rel)) {
            rel = ITagBasic.LINK_REL_STYLESHEET;
        }

        if (StringUtils.isNotBlank(path)) {
            path = path.trim();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCssNames() {
        return href;
    }

    public void setCssNames(String cssNames) {
        this.href = cssNames;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
