/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package inte.com.camel.drools.expert.sample.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import freemarker.template.Template;
import inte.com.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.camel.drools.expert.sample.domain.UserRule;
import com.camel.drools.expert.sample.service.OrderAutoFlowService;
import com.camel.drools.expert.sample.utils.RegisterSDOXsd;
import com.camel.utils.WebContextUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;

/**
 * 集成测试订单自动流转规则
 * @author dengqb
 * @date 2014年8月21日
 */
public class OrderAutoFlowServiceTest extends BaseTest {
    @Autowired
    private OrderAutoFlowService orderAutoFlowService;
    

    /**
     * 获取FreeMarker Template
     * 场景：成功
     */
    @Test
    public void getFreeMarkerTemplateTest() {
        String configFilePath = getClass().getResource("/").getPath();
        System.out.println(configFilePath);
        Template temp = orderAutoFlowService.getTemplate("orderAutoFlowRules.dslr.ftl");
        assertNotNull(temp);
    }
    
    @Test
    public void createDrlFileTest(){
//        ReflectionTestUtils.setField(orderAutoFlowService, "dslFilePath", "/inte/drools/orderAutoFlowRules.dsl" );
//        ReflectionTestUtils.setField(orderAutoFlowService, "drlDirPath", "/inte/drools/drl/" );
        
        UserRule userRule = new UserRule();
        userRule.setUserCode("cef");
        userRule.setRuleName("order flow");
        userRule.getRuleConditionMap().put("expr_104", 50);
        userRule.getRuleConditionMap().put("expr_107", "ebay");
        List<String> destIncludes = new ArrayList<String>();
        destIncludes.add("china");
        destIncludes.add("brazil");
        destIncludes.add("US");
        userRule.getRuleConditionMap().put("expr_109", destIncludes);
        
        
//        DataObject ruleCondition = DataFactory.INSTANCE.create("http://drools.research.com/xsd/RuleCondition", "RuleCondition");
//        ruleCondition.set("amount", 50.00);
//        ruleCondition.set("source", "ebay");
//        List<String> destIncludes = new ArrayList<String>();
//        destIncludes.add("china");
//        destIncludes.add("brazil");
//        destIncludes.add("US");
//        ruleCondition.set("destIncludes", destIncludes);
//        userRule.setRuleCondition(ruleCondition);
        
        
        
        orderAutoFlowService.createDrlFile(userRule);
    }

    /**
     * 由FreeMarker文件生成drools drl文件
     * 场景：成功
     */
//    @Test
//    public void generDslrFromFtlFile_Success() {
//        Template temp = orderAutoFlowService.getTemplate("orderAutoFlowRules.dslr.ftl");
//        UserRule userRule = new UserRule();
//        Map<String, String[]> rules = userRule.getRuleConditionMap();
//        rules.put("expr101", )
//        OrderAutoFlowRule oafr = createOrderAutoFlowRule();
//        
//        List<String> ruleExprs = new ArrayList<String>();
//        oafr.setRuleExpretions(ruleExprs);
//        
//        String drl = orderAutoFlowService.generDslrFromFtlFile(oafr,temp);
//        assertNotNull(drl);
//        System.out.println(drl);
//    }
    
//    @Test
//    public void getDrlFromDslTest_Success(){
//        FreeMarkerConfigurer cfg = getFreeMarkerConfiguration();
//        ReflectionTestUtils.setField(orderAutoFlowService, "freemarkerConfig", cfg );
//        Template temp = orderAutoFlowService.getTemplate("orderAutoFlowRules.ftl");
//        OrderAutoFlowRule oafr = createOrderAutoFlowRule();
//        
//        String dslr = orderAutoFlowService.generDslrFromFtlFile(oafr,temp);
//        
//        assertTrue(dslr !=null);
//        System.out.println(dslr);
//        
//        final Reader dslrReader = new StringReader(dslr);
//        String dslFile = "/inte/com/camel/drools/expert/dynamic/orderAutoFlowRules.dsl";
//        final Reader dslReader = new InputStreamReader(this.getClass().getResourceAsStream(dslFile));
//        String drl = orderAutoFlowService.getDrlFromDsl(dslrReader, dslReader);
//        
//        assertTrue(drl.length() > 1);
//        System.out.println(drl);
//    }
//    
//    /**
//     * 获取drool kbase
//     * 场景：成功
//     */
//    @Test
//    public void getKBaseTest_Success(){
//        FreeMarkerConfigurer cfg = getFreeMarkerConfiguration();
//        ReflectionTestUtils.setField(orderAutoFlowService, "freemarkerConfig", cfg );
//        Template temp = orderAutoFlowService.getTemplate("orderAutoFlowRules.ftl");
//        String dslr = orderAutoFlowService.generDslrFromFtlFile(createOrderAutoFlowRule(),temp);
//        
//        final Reader dslrReader = new StringReader(dslr);
//        String dslFilePath = "/inte/com/camel/drools/expert/dynamic/orderAutoFlowRules.dsl";
//        final Reader dslReader = new InputStreamReader(this.getClass().getResourceAsStream(dslFilePath));
//        String drlStr = orderAutoFlowService.getDrlFromDsl(dslrReader, dslReader);
//        
//        InternalKnowledgeBase kBase = orderAutoFlowService.createKBase(drlStr);
//        assertNotNull(kBase);
//        assertEquals(1, kBase.getKnowledgePackages().size());
//    }
//    
//    /**
//     * 执行drool规则
//     * 场景：成功
//     */
//    @Test
//    public void executeRulesTest_Success(){
//        OrderAutoFlowRule oafr = createOrderAutoFlowRule();
//        
//        FreeMarkerConfigurer cfg = getFreeMarkerConfiguration();
//        ReflectionTestUtils.setField(orderAutoFlowService, "freemarkerConfig", cfg );
//        Template temp = orderAutoFlowService.getTemplate("orderAutoFlowRules.ftl");
//        String dslr = orderAutoFlowService.generDslrFromFtlFile(oafr,temp);
//        
//        final Reader dslrReader = new StringReader(dslr);
//        String dslFile = "/inte/com/camel/drools/expert/dynamic/orderAutoFlowRules.dsl";
//        final Reader dslReader = new InputStreamReader(this.getClass().getResourceAsStream(dslFile));
//        String drlStr = orderAutoFlowService.getDrlFromDsl(dslrReader, dslReader);
//        
//        InternalKnowledgeBase kBase = orderAutoFlowService.createKBase(drlStr);
//        
//        System.out.println(drlStr);
//        KieSession kSession = kBase.newStatefulKnowledgeSession();
//        Order order = createOrder();
//        
//        kSession.insert(order);
//        kSession.insert(oafr);
//        
//        kSession.fireAllRules();
//    }
//    
//    /**
//     * 创建OrderAutoFlowRule对象
//     * @return
//     */
//    private OrderAutoFlowRule createOrderAutoFlowRule(){
//        OrderAutoFlowRule oafr = new OrderAutoFlowRule();
//        oafr.setRuleName("order auto flow");
//        oafr.setCurrency("RMB");
//        oafr.setMaxOrderAmount(99.00);
//        oafr.setMinOrderAmount(10.00);
//        oafr.setOrderSource("ebay");
//        List<Integer> itemIds = new ArrayList<Integer>();
//        itemIds.add(1);
//        itemIds.add(2);
//        itemIds.add(3);
//        oafr.setItemIds(itemIds);
//        
//        List<String> ruleExprs = new ArrayList<String>();
////        ruleExprs.add("- binding currency condition to $currency");
////        ruleExprs.add("- binding order amount condition to $orderAmount");
////        ruleExprs.add("- binding order source condition to $orderSource");
////        ruleExprs.add("- binding item id list condition to $itemIdList");
//        
//        ruleExprs.add("- currency is selective");
//        ruleExprs.add("- amount greater than selective");
//        ruleExprs.add("- order item id is in selective");
//        ruleExprs.add("- amount greater than or equals selective");
//        ruleExprs.add("- amount lesser than selective");
//        
//        oafr.setRuleExpretions(ruleExprs);
//        return oafr;
//    }
//    /**
//     * 创建订单
//     * @return
//     */
//    private Order createOrder() {
//        Order order = new Order();
//        order.setOrderId("5");
//        order.setCurrency("RMB");
//        order.setAmount(100.00);
//        List<Item> items = new ArrayList<Item>();
//        items.add(new Item(3));
//        items.add(new Item(4));
//        order.setItems(items);
//        return order;
//    }
//    
//    private FreeMarkerConfigurer getFreeMarkerConfiguration() {
//        FreeMarkerConfigurer cfg = new FreeMarkerConfigurer();
//        Properties prop = new Properties();
//        prop.setProperty("locale", "zh_CN");
//        prop.setProperty("number_format", "#.####");
//        prop.setProperty("datetime_format", "yyyy-MM-dd");
//        prop.setProperty("date_format", "yyyy-MM-dd");
//        cfg.setFreemarkerSettings(prop);
//        cfg.setTemplateLoaderPath("inte/com/camel/drools/expert/dynamic");
//        cfg.setDefaultEncoding("UTF-8");
//        try {
//            cfg.setConfiguration(cfg.createConfiguration());
//        } catch (IOException | TemplateException e) {
//            e.printStackTrace();
//        }
////        Configuration cfg = new Configuration();
////
////         // Specify the data source where the template files come from. Here I set a
////         // plain directory for it, but non-file-system are possible too:
////        URL url = OrderAutoFlowServiceMockTest.class.getResource("/");
////        String ftlFolder = url.toString()+"inte/com/camel/drools/expert/dynamic";
////        File ftlFolderFile = new File(ftlFolder);
////        System.out.println(ftlFolder);
////        File file = new File("src/test/resources/inte/com/camel/drools/expert/dynamic");
////         try {
////            cfg.setDirectoryForTemplateLoading(file);
////        } catch (IOException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////    
////         // Specify how templates will see the data-model. This is an advanced topic...
////         // for now just use this:
////         cfg.setObjectWrapper(new DefaultObjectWrapper());
////    
////         // Set your preferred charset template files are stored in. UTF-8 is
////         // a good choice in most applications:
////         cfg.setDefaultEncoding("UTF-8");
////    
////         // Sets how errors will appear. Here we assume we are developing HTML pages.
////         // For production systems TemplateExceptionHandler.RETHROW_HANDLER is better.
////         cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
////    
////         // At least in new projects, specify that you want the fixes that aren't
////         // 100% backward compatible too (these are very low-risk changes as far as the
////         // 1st and 2nd version number remains):
////         cfg.setIncompatibleImprovements(new Version(2, 3, 20));  // FreeMarker 2.3.20 
//        return cfg;
//    }
}
