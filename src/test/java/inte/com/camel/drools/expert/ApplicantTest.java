/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package inte.com.camel.drools.expert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.runtime.StatelessKnowledgeSession;

import com.camel.drools.expert.Applicant;
import com.camel.drools.expert.Application;
import com.camel.drools.expert.RuleBase;


/**
 * 
 * @author dengqb
 * @date 2014年3月20日
 */
public class ApplicantTest {
    private static KnowledgeBase kbase;
    private static StatelessKnowledgeSession ksession;
    
    @BeforeClass
    public static void setUp(){
        RuleBase rb = new RuleBase();
        //加载规则
        kbase = rb.readRule();
        //启用一个无状态session
        ksession = kbase.newStatelessKnowledgeSession();
    }
    @Test
    public void testReadRule(){
        Date start = new Date();
        System.out.println(start.getTime());
        Applicant applicant = new Applicant("Mr John Smith",16);
        Application application = new Application(new Date(2014,8,15));
        
        assertTrue(application.isValid());
        //执行规则后，会将valid改为false
        Collection colls = Arrays.asList(new Object[]{application,applicant});
        ksession.execute(colls);
        assertFalse(application.isValid());
//        System.out.println(DateUtils.getTimeDiff(start, new Date()));
    }
}
