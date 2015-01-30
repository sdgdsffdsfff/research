/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.activiti.domain;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author dengqb
 * @date 2015年1月4日
 */
@Component
public class UserBean {
    
    /** injected by Spring */
    @Resource
    private RuntimeService runtimeService;
    
    @Transactional
    public void hello(){
        // here you can do transactional stuff in your domain model
        // and it will be combined in the same transaction as
        // the startProcessInstanceByKey to the Activiti RuntimeService
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("helloProcess");
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
