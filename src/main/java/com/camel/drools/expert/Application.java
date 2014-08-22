/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert;

import java.util.Date;

/**
 * 规则引擎example，驾照申请日期，有效判断
 * @author dengqb
 * @date 2014年3月20日
 */
public class Application {
    private Date dateApplied;
    private boolean valid = true;
    
    public Application(Date dateApplied){
        this.dateApplied = dateApplied;
    }
    
    public Date getDateApplied() {
        return dateApplied;
    }
    public void setDateApplied(Date dateApplied) {
        this.dateApplied = dateApplied;
    }
    public boolean isValid() {
        return valid;
    }
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
}
