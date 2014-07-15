/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved. Use, Copy is subject to authorized license.
 */
package com.camel.framework.domain.base;

import com.camel.framework.dao.base.Constants;

public class BaseQuery {

    private Long id;
    private String sort;
    private Integer start = Integer.valueOf(0);
    private Integer end = Integer.valueOf(10000);
    private Integer pageNo = 1;
    private Integer pageSize = Constants.Page.DEFAULT_PAGE_SIZE;

    private boolean checkBound(Integer i) {
        return i >= 0 && i <= Integer.MAX_VALUE;
    }

    public void setStart(Integer start) {
        if (checkBound(start)) {
            this.start = start;
        }
    }

    public Integer getStart() {
        return start;
    }

    public void setEnd(Integer end) {
        if (checkBound(end)) {
            this.end = end;
        }
    }

    public Integer getEnd() {
        return end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
