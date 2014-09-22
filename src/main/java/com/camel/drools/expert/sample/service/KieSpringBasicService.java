/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.service;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.api.KieBase;
import org.kie.api.cdi.KBase;
import org.kie.api.cdi.KSession;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.kie.internal.runtime.StatelessKnowledgeSession;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.camel.drools.expert.sample.domain.Order;
import com.camel.drools.expert.sample.domain.UserRule;

/**
 * 
 * @author dengqb
 * @date 2014年9月11日
 */
@Component("kieSpringBasicService")
public class KieSpringBasicService {
    @KBase("kBase")
    KieBase kBase;
    
    private static Map<String,InternalKnowledgePackage> packageMap;
    
    private final String drlDirPath="/drools/drl/";
    
    /**
     * 执行规则
     * @param order
     * @param userRule
     */
    public void executeRule(Order order, final UserRule userRule){
        System.out.println("Thread name =" + Thread.currentThread().getId());
        //initKBase();
        loadPackage(userRule);
        KieSession kieStatefulSession = (KieSession) kBase.newKieSession();
//        KieSession kieStatefulSession = kBase.newStatefulKnowledgeSession();
        kieStatefulSession.insert(order);
        kieStatefulSession.insert(userRule);
//        kieStatefulSession.startProcess(userRule.getUserCode());
        kieStatefulSession.fireAllRules(new AgendaFilter(){
            @Override
            public boolean accept(Match match) {
                return match.getRule().getPackageName().endsWith(userRule.getUserCode());
            }
        });
        kieStatefulSession.dispose();
        clearKiePackages();
    }
    
    /**
     * 清空kbase规则库
     */
    private void clearKiePackages(){
        Iterator<KiePackage> it = kBase.getKiePackages().iterator();
        while (it.hasNext()){
            kBase.removeKiePackage(it.next().getName());
        }
    }
    
    /**
     * 加载规则package
     * @param userRule
     */
    private void loadPackage(UserRule userRule){
        if (kBase.getKiePackage("com.camel.drools.expert.sample."+userRule.getUserCode()) != null){
            return;
        }
        
        final KnowledgeBuilderImpl builder = new KnowledgeBuilderImpl();
        File file = new File(KieSpringBasicService.class.getResource("/").getPath().toString()+drlDirPath+userRule.getUserCode()+".drl");
        try {
            String drl = FileUtils.readFileToString(file);
            builder.addPackageFromDrl(new StringReader(drl));
            if (builder.hasErrors()){
                System.out.println("[ERROR: Parsing drl error: "+ builder.getErrors());
            }
        } catch (DroolsParserException | IOException e) {
            e.printStackTrace();
        }
        ((InternalKnowledgeBase)kBase).addPackage(builder.getPackage());
    }
    
    public KieBase getkBase() {
        return kBase;
    }

    public void setkBase(KieBase kBase) {
        this.kBase = kBase;
    }
}
