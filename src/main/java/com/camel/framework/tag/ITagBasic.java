/*
 * Copyright (c) 2014, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.framework.tag;

/**
 * 自定义标签常量类
 * 
 * @author FANGYF
 * @date 2013-5-8
 */
public interface ITagBasic {
    /**
     * 引入js的类型
     */
    String SCRIPT_TYPE_TEXT_JAVASCRIPT = "text/javascript";

    /**
     * link标签的type
     */
    String LINK_TYPE_TEXT_CSS = "text/css";

    /**
     * link标签的rel
     */
    String LINK_REL_STYLESHEET = "stylesheet";

    /**
     * 分隔符,逗号
     */
    String SEPARATOR_COMMA = ",";

    /**
     * 表示使用google minify的常量Y
     */
    String USER_MINIFY_CODE = "Y";

}
