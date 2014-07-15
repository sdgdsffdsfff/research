/*
 * Copyright (c) 2014, FPX and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.camel.framework.domain.base;

import java.io.Serializable;

/**
 * baseVo对象
 * @author 4px
 *
 */
public class BaseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
