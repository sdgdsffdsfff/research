/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package mock.com.camel.drools.expert.sample.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import mock.com.camel.drools.expert.sample.service.domain.Order;
import mock.com.camel.drools.expert.sample.service.domain.RuleCondition;
import mock.com.camel.drools.expert.sample.service.domain.RuleConditionSdo;

import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.impl.InternalKnowledgeBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.kie.internal.KnowledgeBaseFactory;

import com.camel.drools.expert.sample.utils.CglibDynamicBeanGenerator;
import com.camel.drools.expert.sample.utils.RegisterSDOXsd;

/**
 * reproduce dynamic add rule bug
 * @author dengqb
 * @date 2014年9月19日
 */
public class DynamicAddRuleBugTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * using pre-write RuleCondition Object
     * result: fine
     */
    @Test
    public void testReproduceBug() {
        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        String drlFile = "/drools/drl/bug_test1.drl";
        loadPackageFromDrl(kBase, drlFile);
        
        Order order = new Order(50,"ebay","china");
        RuleCondition ruleCondition = new RuleCondition(30,"ebay","china");
        
        KieSession kieSession = kBase.newStatefulKnowledgeSession();
        kieSession.insert(ruleCondition);
        kieSession.insert(order);
        kieSession.fireAllRules(new AgendaFilter(){
            @Override
            public boolean accept(Match match) {
                return match.getRule().getPackageName().endsWith("testbug1");
            }
        });
        //statfull session使用后必需调用dispose
        kieSession.dispose();
        
        System.out.println("========add new rule=================");
        String drlFile2 = "/drools/drl/bug_test2.drl";
        loadPackageFromDrl(kBase, drlFile2);
        
        kieSession = kBase.newStatefulKnowledgeSession();
        Order order2 = new Order(32.0,"ebay","brazil");
        RuleCondition ruleCondition2 = new RuleCondition(30,"ebay","brazil");
        kieSession.insert(ruleCondition2);
        kieSession.insert(order2);
        kieSession.fireAllRules(new AgendaFilter(){
            @Override
            public boolean accept(Match match) {
                return match.getRule().getPackageName().endsWith("testbug2");
            }
        });
        kieSession.dispose();
    }

    /**
     * using a RuleConditonSdo object, it is dynamically created by SDO technic
     * result: false
     */
    @Test
    public void testReproduceBugWithSDO() {
        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        String drlFile = "/drools/drl/bug_sdo_test1.drl";
        loadPackageFromDrl(kBase, drlFile);
        
        Order order = new Order(50,"ebay","china");
        
        //注册sdo对象
        RegisterSDOXsd register = new RegisterSDOXsd();
        register.setXsdFilePath("/drools/xsd/xsdList.txt");
        register.register();
        
        RuleConditionSdo rcsdo = new RuleConditionSdo();
        rcsdo.getRuleCondition().setDouble("minPrice",30);
        rcsdo.getRuleCondition().setString("source","ebay");
        rcsdo.getRuleCondition().setString("dest","china");
        
        KieSession kieSession = kBase.newStatefulKnowledgeSession();
        kieSession.insert(rcsdo);
        kieSession.insert(order);
        kieSession.fireAllRules(new AgendaFilter(){
            @Override
            public boolean accept(Match match) {
                return match.getRule().getPackageName().endsWith("testbug1");
            }
        });
        //statfull session使用后必需调用dispose
        kieSession.dispose();
        
        System.out.println("========add new rule=================");
        String drlFile2 = "/drools/drl/bug_sdo_test2.drl";
        loadPackageFromDrl(kBase, drlFile2);
        
        kieSession = kBase.newStatefulKnowledgeSession();
        Order order2 = new Order(32.0,"ebay","brazil");
        //RuleCondition ruleCondition2 = new RuleCondition(30,"ebay","brazil");
        RuleConditionSdo rcsdo2 = new RuleConditionSdo();
        rcsdo.getRuleCondition().setDouble("minPrice",30);
        rcsdo.getRuleCondition().setString("source","ebay");
        rcsdo.getRuleCondition().setString("dest","breazil");
        kieSession.insert(rcsdo2);
        kieSession.insert(order2);
        kieSession.fireAllRules(new AgendaFilter(){
            @Override
            public boolean accept(Match match) {
                return match.getRule().getPackageName().endsWith("testbug2");
            }
        });
        kieSession.dispose();
    }
    
    /**
     * using a dynamicl RuleCondition object, it created by cglib BeanGenerator
     * result: fine
     * @throws ClassNotFoundException
     */
    @Test
    public void testReproduceBugWithDynamicBean() throws ClassNotFoundException{
        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        String drlFile = "/drools/drl/bug_dyb_test1.drl";
        loadPackageFromDrl(kBase, drlFile);
        
        Order order = new Order(50,"ebay","china");
        
        Map<String,Class<?>> properties = new HashMap<String,Class<?>>();
        properties.put("minPrice", Class.forName("java.lang.Double"));  
        properties.put("source", Class.forName("java.lang.String"));  
        properties.put("dest", Class.forName("java.lang.String"));  
        // 生成动态 Bean  
        CglibDynamicBeanGenerator ruleCondition = new CglibDynamicBeanGenerator(properties);
        ruleCondition.setValue("minPrice", 40d);
        ruleCondition.setValue("source", "ebay");
        ruleCondition.setValue("dest", "china");
        
        KieSession kieSession = kBase.newStatefulKnowledgeSession();
        kieSession.insert(ruleCondition);
        kieSession.insert(order);
        kieSession.fireAllRules(new AgendaFilter(){
            @Override
            public boolean accept(Match match) {
                return match.getRule().getPackageName().endsWith("testbug1");
            }
        });
        //statfull session使用后必需调用dispose
        kieSession.dispose();
        
        System.out.println("========add new rule=================");
        String drlFile2 = "/drools/drl/bug_dyb_test2.drl";
        loadPackageFromDrl(kBase, drlFile2);
        
        kieSession = kBase.newStatefulKnowledgeSession();
        Order order2 = new Order(32.0,"ebay","brazil");
        
        ruleCondition = new CglibDynamicBeanGenerator(properties);
        ruleCondition.setValue("minPrice", 30d);
        ruleCondition.setValue("source", "ebay");
        ruleCondition.setValue("dest", "brazil");
        
        kieSession.insert(ruleCondition);
        kieSession.insert(order2);
        kieSession.fireAllRules(new AgendaFilter(){
            @Override
            public boolean accept(Match match) {
                return match.getRule().getPackageName().endsWith("testbug2");
            }
        });
        kieSession.dispose();
    }
    private void loadPackageFromDrl(InternalKnowledgeBase kBase, String drlFile) {
        final Reader drlReader = new InputStreamReader(this.getClass().getResourceAsStream(drlFile));
        
        final KnowledgeBuilderImpl builder = new KnowledgeBuilderImpl();
        try {
            builder.addPackageFromDrl(drlReader);
            if (builder.hasErrors()){
                System.out.println(builder.getErrors());
            }
            kBase.addPackage(builder.getPackage());
        } catch (DroolsParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
