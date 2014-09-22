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
    
    private double price;
    private int saledNum;
    private String status;
    
    public ERPCondition(){
        
    }
    
    public ERPCondition(double price, int saledNum, String status){
        this.price = price;
        this.saledNum = saledNum;
//        this.erp_status = status;
    }

    public double getPrice() {
        return price;
    }

    public int getSaledNum() {
        return saledNum;
    }

    public String getStatus() {
        return status;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSaledNum(int saledNum) {
        this.saledNum = saledNum;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
