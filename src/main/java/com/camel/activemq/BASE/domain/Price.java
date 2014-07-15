/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.activemq.BASE.domain;

import com.camel.framework.domain.base.BaseEntity;

/**
 * 
 * @author dengqb
 * @date 2014年4月30日
 */
public class Price extends BaseEntity implements java.io.Serializable{
    
    private int userid;
    private double price;
    
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}
