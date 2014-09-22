/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.controller.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.camel.drools.expert.sample.domain.Order;
import com.camel.drools.expert.sample.domain.UserRule;
import com.camel.drools.expert.sample.service.OrderAutoFlowService;
import com.camel.drools.expert.sample.service.OrderFlowControlService;

import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;

/**
 * 规则congroller
 * @author dengqb
 * @date 2014年8月23日
 */
@Controller
@RequestMapping(value="/drools/")
public class DroolsRuleController {
    
    @Resource
    private OrderAutoFlowService orderAutoFlowService;
    @Resource
    private OrderFlowControlService orderFlowControlService;

    @RequestMapping(value="rule/index")
    public ModelAndView getRulePage(){
        return new ModelAndView("drools/rulePage");
    }
    
    @RequestMapping(value="rule/new")
    public ModelAndView createRule(HttpServletRequest request, HttpServletResponse response){
        Map<String,String[]> paramMap = request.getParameterMap();
        
        UserRule userRule = new UserRule();
        try {
            DataObject ruleCondDSO = userRule.loadRuleCondition(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        orderAutoFlowService.createDrlFile(userRule);
        
        return new ModelAndView("drools/rulePage");
    }
    
    @RequestMapping(value="rule/submit-order")
    public ModelAndView submitOrder(Order order){
        //模拟用户存放的条件设定值
        String rcJson = "{\"expr_104\":50,\"expr_107\":\"ebay\",\"expr_109\":[\"china\",\"brazil\",\"US\"]}";
        
        UserRule userRule = new UserRule();
        userRule.setUserCode("drl1");
        userRule.loadRuleConditionFromJson(rcJson);
        orderFlowControlService.processOrder(order,userRule);
        
        return new ModelAndView("drools/rulePage");
    }
}
