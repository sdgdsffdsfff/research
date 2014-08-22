/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package inte.com.camel.drools.expert.dynamic;

import static org.junit.Assert.fail;

import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.lang.descr.AndDescr;
import org.drools.compiler.lang.descr.BindingDescr;
import org.drools.compiler.lang.descr.ExprConstraintDescr;
import org.drools.compiler.lang.descr.OrDescr;
import org.drools.compiler.lang.descr.PackageDescr;
import org.drools.compiler.lang.descr.PatternDescr;
import org.drools.compiler.lang.descr.RuleDescr;
import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.definitions.rule.impl.RuleImpl;
import org.drools.core.impl.InternalKnowledgeBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.kie.internal.KnowledgeBaseFactory;

import com.camel.drools.expert.Applicant;
import com.camel.drools.expert.dynamic.DynamicRuleEngineImpl;
import com.camel.drools.expert.dynamic.IDynamicRuleEngine;

/**
 * 动态规则引擎测试类
 * @author dengqb
 * @date 2014年8月12日
 */
public class DynamicRuleEngineImplTest {
    private IDynamicRuleEngine dynamicRuleEngine;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        dynamicRuleEngine = new DynamicRuleEngineImpl();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 获取新用户RuleBase
     * 场景：没有规则package
     */
    @Test
    public void getRuleBaseTest_newUserNoRule(){
//        final String userCode = "camel";
//        UserRules.removeRuleBase(userCode);
//        //RuleBase ruleBase= dynamicRuleEngine.getRuleBase(userCode);
//        assertNotNull(ruleBase);
//        assertTrue(ruleBase.getPackages().length == 0);
    }
    
    /**
     * 解析规则参数，转变为规则
     * 场景：解析成功
     */
    @Test
    public void paramAnalyseTest_Success() {
        fail("Not yet implemented");
    }
    
    /**
     * 动态创建规则
     * 场景：创建成功
     */
    @Test
    public void dynamictRuleCreateTest_Success() {
//        final String userCode = "camel";
//        Rule ruleBase= dynamicRuleEngine.getRuleBase(userCode);
        final KnowledgeBuilderImpl builder = new KnowledgeBuilderImpl();
        //package name 必须和java bean的目录一致，
        //否则PackageBuilder.addPackage()时触发规则编译，会找不到规则中的类
        final String packageName = "com.camel.drools.expert"; 
        
        final PackageDescr packageDescr = new PackageDescr(packageName);
        final RuleDescr ruleDescr = new RuleDescr("is valid age");
        
        packageDescr.addRule( ruleDescr );
        
        //When and判断条件
        final AndDescr lhs = new AndDescr();
        //final OrDescr lhs = new OrDescr();
        ruleDescr.setLhs(lhs);
        
        final PatternDescr patternDescr = new PatternDescr(Applicant.class.getName(),"$a");
        lhs.addDescr(patternDescr);
        
//        BindingDescr fieldBindingDescr = new BindingDescr("$a", "age");
//        patternDescr.addConstraint(fieldBindingDescr);
//        fieldBindingDescr = new BindingDescr("$s", "sex");
//        patternDescr.addConstraint(fieldBindingDescr);
        patternDescr.addConstraint(new ExprConstraintDescr("age < 18"));
        patternDescr.addConstraint(new ExprConstraintDescr("sex == \"M\""));
        
        //then 逻辑
        ruleDescr.setConsequence("System.out.println($a.getName());");
        
        builder.addPackage(packageDescr);
        
        InternalKnowledgePackage pkg = builder.getPackage();
        RuleImpl rule = pkg.getRule("is valid age");
        
        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        kBase.addPackage(pkg);
        
        final KieSession workingMemory = kBase.newStatefulKnowledgeSession();
        
        //创建好KieSession后，传入fact application
        Applicant applicant = new Applicant("Mr John Smith",16,"M");
        workingMemory.insert(applicant);
        
        //执行规则
        workingMemory.fireAllRules(new AgendaFilter() {
            @Override
            public boolean accept(Match match) {
                return match.getRule().getName().contains("is valid age");
            }
        });
//        if (!builder.hasErrors()){
//        }else{
//            System.out.println("builder validation errors!");
//            fail("paclage builder validation errors!");
//        }
        //assertTrue(dynamicRuleEngine.getRuleBase(userCode).getPackage("com.camel.drools.expert") != null);
    }
    
    /**
     * 动态创建规则
     * 场景：规则校验失败
     */
    @Test
    public void dynamictRuleCreateTest_RuleValidateFail() {
        fail("Not yet implemented");
    }
    
    /**
     * 执行所有规则
     * 场景：成功
     */
    @Test
    public void executeAllRulesTest_Success() {
        fail("Not yet implemented");
    }
    
    /**
     * 执行指定规则
     * 场景：成功
     */
    @Test
    public void executeRuleTest_Success() {
        fail("Not yet implemented");
    }

}
