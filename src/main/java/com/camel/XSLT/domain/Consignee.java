package com.camel.XSLT.domain;

/**
 * 收件人信息
 * 
 * @author dengqb
 * @date 2015年6月8日
 */
public class Consignee {
    /**
     * 收件人姓名
     */
    private String name;
    /**
     * 收件人地址
     */
    private String address;
    /**
     * 收件人地址邮编
     */
    private String postCode;
    
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPostCode() {
        return postCode;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
