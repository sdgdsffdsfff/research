/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.domain;

/**
 * javabean 用户规则
 * @author dengqb
 * @date 2014年9月9日
 */
public class UserRuleCondition {
    private Double amount_ur;
    private String source_ur;
    
    private String userCode_ur;
    
    public UserRuleCondition(){
        
    }

    public Double getAmount_ur() {
        return amount_ur;
    }

    public String getSource_ur() {
        return source_ur;
    }

    public String getUserCode_ur() {
        return userCode_ur;
    }

    public void setAmount_ur(Double amount_ur) {
        this.amount_ur = amount_ur;
    }

    public void setSource_ur(String source_ur) {
        this.source_ur = source_ur;
    }

    public void setUserCode_ur(String userCode_ur) {
        this.userCode_ur = userCode_ur;
    }
    
}
