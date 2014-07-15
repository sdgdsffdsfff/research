/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.framework.domain.base;

import org.springframework.data.annotation.Id;

/**
 * mongo实体类抽取出ID的公用模块
 * 
 * @author Shuah
 * @date 2014年3月10日
 */
public class BaseStrEntity implements java.io.Serializable {
    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键ID
     */
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
