package com.camel.XSLT.domain;

import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement
public class Order {

    private String productName;
    private Double sellPrice;
    private int quantity;
    
    public String getProductName() {
        return productName;
    }
    public Double getSellPrice() {
        return sellPrice;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
