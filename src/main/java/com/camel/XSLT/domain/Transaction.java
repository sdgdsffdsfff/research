package com.camel.XSLT.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * 交易信息
 * 
 * @author dengqb
 * @date 2015年6月8日
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {
    /**
     * 交易id
     */
    //@XmlElement
    private String transId;
    /**
     * 交易编号
     */
    //@XmlElement
    private String transNo;
    /**
     * 交易总价，应付价
     */
    private Double totalPrice;
    /**
     * 收件人信息
     */
    private Consignee consignee;
    /**
     * 交易订单，一个item算一个订单
     */
    @XmlElementWrapper(name="orders")
    @XmlElement(name="order")
    private List<Order> orders;
    
    public String getTransId() {
        return transId;
    }
    public String getTransNo() {
        return transNo;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public Consignee getConsignee() {
        return consignee;
    }
    public void setTransId(String transId) {
        this.transId = transId;
    }
    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setConsignee(Consignee consignee) {
        this.consignee = consignee;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
