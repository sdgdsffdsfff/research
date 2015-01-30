/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package inte.com.camel.activiti;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.utils.AOPDynamicConfigurator.envparam.ConfigParamManager;
import com.utils.AOPDynamicConfigurator.envparam.ConfigParamMap;
import com.utils.AOPDynamicConfigurator.javassistaop.EnvConfigAOPManager;

/**
 * 
 * @author dengqb
 * @date 2015年1月7日
 */
public class MainTest {
    @Resource
    private RepositoryService repositoryService;
    
    @Resource
    private TaskService taskService;
    
    @Resource
    private RuntimeService runtimeService;
    
    private void loadBPMNxml(){
        repositoryService.createDeployment()
        .addClasspathResource("activiti/repository/hello.bpmn")
        .deploy()
        .getId();
    }
    
    public static void main(String[] args) {
        ConfigParamManager cpm = new ConfigParamManager();
        cpm.readConfig();
        
        EnvConfigAOPManager.initAOP();
        System.out.println(ConfigParamMap.getValue("jdbc.url"));
        System.out.println("active.mq.cec.wmc.order="+ConfigParamMap.getValue("active.mq.cec.wmc.order"));
        
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(  
                new String[] {"applicationContext-test.xml","applicationContext-activiti-test.xml"});  
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext  
                .getAutowireCapableBeanFactory(); 
        
        MainTest mt = new MainTest();
        mt.repositoryService = (RepositoryService) beanFactory.getBean("repositoryService");
        mt.runtimeService = (RuntimeService) beanFactory.getBean("runtimeService");
        
        mt.loadBPMNxml();
        
        StopWatch stopWatch = new LoggingStopWatch("activiti started");
        
        ProcessInstance pi = mt.runtimeService.startProcessInstanceByKey("helloProcess");
        Execution execution = mt.runtimeService.createExecutionQuery()
                .processInstanceId(pi.getId())
                .activityId("receivetask1")
                .singleResult();
        System.out.println("execution id="+execution.getId());
        ExecutionQuery eq = mt.runtimeService.createExecutionQuery().processInstanceId(pi.getId());
        List<Execution> exes = eq.list();
        assertEquals(2,exes.size());
        
        assertEquals("receivetask1",mt.runtimeService.getActiveActivityIds(pi.getId()).get(0));
        //runtimeService.signal(execution.getId());
        stopWatch.stop();
    }
}
