package com.camel.newservicearch.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderCD;
    private String title;
    private Double price;
    private String address;
    private String skuCode;
    private int skuNum;
    
    private List<OrderPackage> pkgs;
    
    public String getOrderCD() {
        return orderCD;
    }
    public String getTitle() {
        return title;
    }
    public Double getPrice() {
        return price;
    }
    public void setOrderCD(String orderCD) {
        this.orderCD = orderCD;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSkuCode() {
        return skuCode;
    }
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    public List<OrderPackage> getPkgs() {
        if (pkgs == null){
            pkgs = new ArrayList<OrderPackage>();
        }
        return pkgs;
    }
    public void setPkgs(List<OrderPackage> pkgs) {
        this.pkgs = pkgs;
    }
    
    public void addPkg(OrderPackage op) {
        getPkgs().add(op);
    }
    public int getSkuNum() {
        return skuNum;
    }
    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }
}
