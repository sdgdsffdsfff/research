/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.api.runtime.KieContext;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.springframework.stereotype.Service;

import com.camel.drools.expert.sample.domain.Order;
import com.camel.drools.expert.sample.domain.UserRule;

import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;

/**
 * 订单流转控制服务
 * @author dengqb
 * @date 2014年9月2日
 */
@Service (value="orderFlowControlService")
public class OrderFlowControlService {
    @Resource
    private KieSpringBasicService kieSpringBasicService;
    
    public void processOrder(Order order, final UserRule userRule){
        kieSpringBasicService.executeRule(order, userRule);
    }
}
