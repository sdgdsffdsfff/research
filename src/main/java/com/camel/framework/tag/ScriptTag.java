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
 * 引入js的自定义标签
 * 
 * @author FANGYF
 * @date 2013-5-8
 */
public class ScriptTag extends TagSupport {

    private static final long serialVersionUID = -4393216367135816017L;

    private static final Logger logger = LoggerFactory.getLogger(TagSupport.class);

    // scprit标签的type
    private String type;

    // 引入js的路径
    private String path;

    // 引入js的源文件，多个以逗号隔开
    private String src;

    // 适应seaJs增加此属性,加载配置
    private String data_config;

    // 适应seaJs增加此属性,加载完成后执行的js
    private String data_main;

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        this.pageContext.getServletConfig();
        // 初始化参数
        initParam();
        // 解析标签
        parse();
        return SKIP_BODY;
    }

    /**
     * 根据配置解析标签并生成新的标签，一种是Coogle Minify模式,一种是一般的模式
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
            // 将标签解析成 Minify格式的script标签再输出到页面
            parseToMinifyStyle(sb, resourcesUrl, out);
        } else {
            // 将标签解析成非 Minify格式的script标签再输出到页面
            parseToConventionalStyle(sb, resourcesUrl, out);
        }
    }

    /**
     * 将标签解析成常规格式的script标签再输出到页面
     * 
     * @param sb
     * @param out
     */
    private void parseToConventionalStyle(StringBuffer sb, String resourcesUrl, JspWriter out) {
        // 将js源文件字符串以逗号拆分成数组
        String[] srcArray = src.split(ITagBasic.SEPARATOR_COMMA);

        for (String srcStr : srcArray) {
            // 清空原有的内容
            sb.setLength(0);
            // 按照一般格式拼接script标签
            sb.append("<script type='").append(type).append("' data-config='").append(data_config).append(
                    "' data-main='").append(data_main).append("' src='").append(resourcesUrl);

            // path 可以为空，故需要进行判断
            if (StringUtils.isNotBlank(path)) {
                sb.append(path).append("/");
            }

            sb.append(srcStr.trim()).append("'></script>");

            try {
                // 将解析后的script标签输出到页面
                out.println(sb.toString());

            } catch (IOException e) {
                logger.error("Could not print out value '" + sb.toString(), e);
            }
        }
    }

    /**
     * 将标签解析成 Minify格式的script标签再输出到页面
     * 
     * @param sb
     * @param resourcesUrl
     * @param out
     */
    private void parseToMinifyStyle(StringBuffer sb, String resourcesUrl, JspWriter out) {
        sb.setLength(0);
        sb.append("<script type='").append(type).append("' data-config='").append(data_config).append("' data-main='").append(
                data_main).append("' src='").append(resourcesUrl).append("min/?");

        // path为空的情况下不设置b
        if (StringUtils.isNotBlank(path)) {
            sb.append("b=").append(path).append("&");
        }

        sb.append("f=").append(src).append("'></script>");

        try {
            // 将解析后的script标签输出到页面
            out.println(sb.toString());

        } catch (IOException e) {
            logger.error("Could not print out value '" + sb.toString(), e);
        }
    }

    /**
     * 初始化script标签的一些属性
     */
    private void initParam() {
        // 如果没有指定script的type则默认为"text/javascript"
        if (StringUtils.isBlank(type)) {
            type = ITagBasic.SCRIPT_TYPE_TEXT_JAVASCRIPT;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * @return the data_config
     */
    public String getData_config() {
        return data_config;
    }

    /**
     * @param data_config the data_config to set
     */
    public void setData_config(String data_config) {
        this.data_config = data_config;
    }

    /**
     * @return the data_main
     */
    public String getData_main() {
        return data_main;
    }

    /**
     * @param dataMain the data_main to set
     */
    public void setData_main(String dataMain) {
        this.data_main = dataMain;
    }

}
