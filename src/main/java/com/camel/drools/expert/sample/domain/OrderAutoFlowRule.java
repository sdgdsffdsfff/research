/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.domain;

import java.util.List;

/**
 * 用户选择的订单自动流程规则，及针对规则设定的条件
 * @author dengqb
 * @date 2014年8月21日
 */
public class OrderAutoFlowRule {
    /**
     * 规则名称
     */
    private String ruleName;
    
    //==========以下属性是用户可设置的条件参数==============
    /**
     * 币种条件
     */
    private String currency;
    /**
     * 订单总金额
     */
    private double orderAmount;
    /**
     * 订单来源：ebay|smt|amazon
     */
    private String orderSource;
    /**
     * 订单必须包含的item id
     */
    private List<Integer> itemIds;
    
    /**
     * 用户已选择的规则表达式
     */
    private List<String> ruleExpretions;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    public List<String> getRuleExpretions() {
        return ruleExpretions;
    }

    public void setRuleExpretions(List<String> ruleExpretions) {
        this.ruleExpretions = ruleExpretions;
    }
}
