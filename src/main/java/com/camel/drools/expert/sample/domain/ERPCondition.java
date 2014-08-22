/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.domain;

/**
 * ERP(ebay return policy) ebay退费规则条件
 * @author dengqb
 * @date 2014年8月19日
 */
public class ERPCondition {
    
    private double erp_price;
    private int erp_saledNum;
    private String erp_status;
    
    public ERPCondition(){
        
    }
    
    public ERPCondition(double price, int saledNum, String status){
        this.erp_price = price;
        this.erp_saledNum = saledNum;
        this.erp_status = status;
    }
    
    
    public double getErp_price() {
        return erp_price;
    }
    public void setErp_price(double erp_price) {
        this.erp_price = erp_price;
    }
    public int getErp_saledNum() {
        return erp_saledNum;
    }
    public void setErp_saledNum(int erp_saledNum) {
        this.erp_saledNum = erp_saledNum;
    }
    public String getErp_status() {
        return erp_status;
    }
    public void setErp_status(String erp_status) {
        this.erp_status = erp_status;
    }
}
