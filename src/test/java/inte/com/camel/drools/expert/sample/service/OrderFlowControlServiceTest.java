/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package inte.com.camel.drools.expert.sample.service;

import inte.com.BaseTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.camel.drools.expert.sample.domain.Item;
import com.camel.drools.expert.sample.domain.Order;
import com.camel.drools.expert.sample.domain.UserRule;
import com.camel.drools.expert.sample.service.OrderAutoFlowService;
import com.camel.drools.expert.sample.service.OrderFlowControlService;
import com.camel.framework.utils.DateUtils;
import com.camel.framework.utils.LogUtils;

/**
 * 订单流程控制类
 * @author dengqb
 * @date 2014年9月5日
 */
public class OrderFlowControlServiceTest extends BaseTest {
    @Resource
    private OrderFlowControlService orderFlowControlService;
    @Resource
    private OrderAutoFlowService orderAutoFlowService;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void init() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * 测试单一订单通过规则引擎
     * Test method for {@link com.camel.drools.expert.sample.service.OrderFlowControlService#processOrder(com.camel.drools.expert.sample.domain.Order)}.
     */
    @Test
    public void testProcessOrder() {
        List<Item> items = new ArrayList<Item>();
        Item item1= new Item(1);
        items.add(item1);
        Item item2= new Item(2);
        items.add(item2);
        Item item3= new Item(3);
        items.add(item3);
        
        Order order = new Order(50,"ebay","china");
        
        //模拟用户存放的条件设定值
        String rcJson = "{\"expr_104\":50,\"expr_107\":\"ebay\",\"expr_109\":[\"china\",\"brazil\",\"US\"]}";
        UserRule userRule = new UserRule();
        userRule.setUserCode("drl1");
        userRule.loadRuleConditionFromJson(rcJson);
        
        orderFlowControlService.processOrder(order, userRule);
    }
    
    /**
     * 测试多个订单通过规则引擎
     */
    @Test
    public void testProcessHugeOrder(){
        
        //模拟用户存放的条件设定值
        String rcJson = "{\"expr_104\":50,\"expr_107\":\"ebay\",\"expr_109\":[\"china\",\"brazil\",\"US\"]}";
        //不同订单对象，由同一规则处理
        for (int i=1; i<=5; i++){
            Order order = new Order();
            order.setAmount(50);
            order.setSource("ebay");
            order.setDestination("china");
            
            UserRule userRule = new UserRule();
            userRule.setUserCode("drl1");
            userRule.loadRuleConditionFromJson(rcJson);
            orderFlowControlService.processOrder(order, userRule);
        }
        System.out.println("第一次规则执行完成");
        //再次执行
      //不同订单对象，由不同规则处理
        for (int i=1; i<=5; i++){
            Order order = new Order();
            order.setAmount(50);
            order.setSource("ebay");
            order.setDestination("china");
            
            UserRule userRule = new UserRule();
            userRule.setUserCode("drl"+i);
            userRule.loadRuleConditionFromJson(rcJson);
            orderFlowControlService.processOrder(order, userRule);
        }
        System.out.println("第二次规则执行完成");
        
      //再次执行
      //不同订单对象，由不同规则处理
        for (int i=10; i<=20; i++){
            Order order = new Order();
            order.setAmount(50);
            order.setSource("ebay");
            order.setDestination("china");
            
            UserRule userRule = new UserRule();
            userRule.setUserCode("drl"+i);
            userRule.loadRuleConditionFromJson(rcJson);
            orderFlowControlService.processOrder(order, userRule);
        }
    }
    
    /**
     * 测试多线程处理
     */
    @Test
    public void testMultithreadProcessOrder() {
      //模拟用户存放的条件设定值
      String rcJson = "{\"expr_104\":50,\"expr_107\":\"ebay\",\"expr_109\":[\"china\",\"brazil\",\"US\"]}";
        
      final ExecutorService exec = Executors.newFixedThreadPool(3);
      
      LogUtils logUtils = new LogUtils();
      logUtils.beforeRun("start rule engin process");
      for (int i=10; i<=1000; i++){
          Order order = new Order();
          order.setAmount(50);
          order.setSource("ebay");
          order.setDestination("china");
          
          UserRule userRule = new UserRule();
          userRule.setUserCode("drl"+i);
          userRule.loadRuleConditionFromJson(rcJson);
          Future<String> task = exec.submit(new ruleExecutor(order,userRule));
      }
      logUtils.afterRun("finished rule engin process");
    }
    
    private class ruleExecutor implements Callable {
        private Order order;
        private UserRule userRule;
        
        public ruleExecutor(Order order, UserRule userRule){
            this.order = order;
            this.userRule = userRule;
        }
        @Override
        public Object call() throws Exception {
            orderFlowControlService.processOrder(order, userRule);
            return "finished";
        }
    }
    
    @Test 
    public void testDynamicAddRuleBugs(){
      //模拟用户存放的条件设定值
        String rcJson = "{\"expr_104\":50,\"expr_107\":\"ebay\",\"expr_109\":[\"china\",\"brazil\",\"US\"]}";
        //不同订单对象，由同一规则处理
        Order order = new Order();
        order.setAmount(50);
        order.setSource("ebay");
        order.setDestination("china");
        
        UserRule userRule = new UserRule();
        userRule.setUserCode("drl1");
        userRule.loadRuleConditionFromJson(rcJson);
        orderFlowControlService.processOrder(order, userRule);
        
        UserRule userRule2 = new UserRule();
        userRule2.setUserCode("drl2");
        userRule.loadRuleConditionFromJson(rcJson);
        orderFlowControlService.processOrder(order, userRule2);
    }
//
//    @Test
//    public void testRepeatAddPackageAndExecuteIn2Methods(){
//      //模拟用户存放的条件设定值
//        String rcJson = "{\"expr_104\":50,\"expr_107\":\"ebay\",\"expr_109\":[\"china\",\"brazil\",\"US\"]}";
//        for (int i=1; i<=2; i++){
//            Order order = new Order();
//            order.setAmount(50);
//            order.setSource("ebay");
//            order.setDestination("china");
//            
//            UserRule userRule = new UserRule();
//            userRule.setUserCode("drl"+i);
//            userRule.loadRuleConditionFromJson(rcJson);
//            orderFlowControlService.loadPackage(userRule);
//            orderFlowControlService.processOrder(order, userRule);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    
    /**
     * 循环测试多个drl文件，用于压力测试和检测后台资源使用
     */
    @Test
    public void loopCreateDrl(){
        UserRule userRule = new UserRule();
        userRule.setRuleName("order flow");
        userRule.getRuleConditionMap().put("expr_104", 50);
        userRule.getRuleConditionMap().put("expr_107", "ebay");
        List<String> destIncludes = new ArrayList<String>();
        destIncludes.add("china");
        destIncludes.add("brazil");
        destIncludes.add("US");
        userRule.getRuleConditionMap().put("expr_109", destIncludes);
        
        for (int i=1; i<=1000; i++){
            userRule.setUserCode("drl"+i);
            orderAutoFlowService.createDrlFile(userRule);
        }
    }

}
