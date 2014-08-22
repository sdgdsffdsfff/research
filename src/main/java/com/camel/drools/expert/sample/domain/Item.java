/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.domain;

/**
 * 刊登商品
 * @author dengqb
 * @date 2014年8月18日
 */
public class Item {
    private Integer id;
    private String title;
    private double price;
    private int saledNum;
    private String status;
    
    public Item(){
        
    }
    public Item(Integer id){
        this.id = id;
    }
    public Item(String title, double price, int saledNum, String status){
        this.title = title;
        this.price = price;
        this.saledNum = saledNum;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getSaledNum() {
        return saledNum;
    }
    public void setSaledNum(int saledNum) {
        this.saledNum = saledNum;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
