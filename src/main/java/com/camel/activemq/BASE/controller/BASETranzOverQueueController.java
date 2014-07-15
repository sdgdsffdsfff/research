/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.activemq.BASE.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.camel.activemq.BASE.IQueueService;

/**
 * 通过queue方式达到最终一致性（BASE）的解决方案
 * @author dengqb
 * @date 2014年5月4日
 */
@Controller
@RequestMapping(value="/BASE/")
public class BASETranzOverQueueController {

    @Resource
    private IQueueService queueServiceImpl;
    
    @RequestMapping(value="index")
    public String goIndex(){
        return "BASE/BASE_tranz";
    }
    
    @RequestMapping(value="tranz_test")
    public ModelAndView tranzTest(){
        String msgBody1 = "update local user price from 2 to 5";
        String msgBody2 = "update remote user price from 2 to 5";
        try {
            queueServiceImpl.transactionSendWithDB(msgBody1, msgBody2);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return new ModelAndView("BASE/BASE_tranz");
    }
}
