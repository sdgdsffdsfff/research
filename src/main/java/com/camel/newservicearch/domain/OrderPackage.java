package com.camel.newservicearch.domain;

public class OrderPackage {
    
    private String orderCD;
    private String pkgCD;
    private String skuCode;
    private int skuNum;
    /**
     * 预报返回的单据号
     */
    private String deliveryCode;
    
    public String getOrderCD() {
        return orderCD;
    }
    public String getPkgCD() {
        return pkgCD;
    }
    public String getSkuCode() {
        return skuCode;
    }
    public int getSkuNum() {
        return skuNum;
    }
    public void setOrderCD(String orderCD) {
        this.orderCD = orderCD;
    }
    public void setPkgCD(String pkgCD) {
        this.pkgCD = pkgCD;
    }
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }
    public String getDeliveryCode() {
        return deliveryCode;
    }
    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }
}
