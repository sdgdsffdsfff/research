///*
// * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
// * Use, Copy is subject to authorized license.
// */
//package mock.com.camel.drools.expert.sample.service;
//
//import static org.junit.Assert.*;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.StringReader;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//import mockit.Tested;
//
//import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
//import org.drools.compiler.compiler.DroolsParserException;
//import org.drools.compiler.compiler.PackageBuilderErrors;
//import org.drools.core.definitions.InternalKnowledgePackage;
//import org.drools.core.definitions.impl.KnowledgePackageImpl;
//import org.drools.core.impl.InternalKnowledgeBase;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.kie.api.runtime.KieSession;
//import org.kie.internal.KnowledgeBaseFactory;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//
//import com.camel.drools.expert.sample.domain.Item;
//import com.camel.drools.expert.sample.domain.Order;
//import com.camel.drools.expert.sample.domain.UserRule;
//import com.camel.drools.expert.sample.service.OrderAutoFlowService;
//
//import freemarker.template.Configuration;
//import freemarker.template.DefaultObjectWrapper;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import freemarker.template.TemplateExceptionHandler;
//import freemarker.template.Version;
//
///**
// * 测试订单自动流转规则
// * @author dengqb
// * @date 2014年8月21日
// */
//public class OrderAutoFlowServiceMockTest {
//    @Tested
//    OrderAutoFlowService orderAutoFlowService = new OrderAutoFlowService();
//    
//    /**
//     * @throws java.lang.Exception
//     */
//    @Before
//    public void setUp() throws Exception {
//        
//    }
//
//    /**
//     * @throws java.lang.Exception
//     */
//    @After
//    public void tearDown() throws Exception {
//    }
//
//    /**
//     * 获取FreeMarker Template
//     * 场景：成功
//     */
//    @Test
//    public void getFreeMarkerTemplateTest_Success() {
//        String configFilePath = getClass().getResource("/").getPath();
//        System.out.println(configFilePath);
//        FreeMarkerConfigurer cfg = getFreeMarkerConfiguration();
//        ReflectionTestUtils.setField(orderAutoFlowService, "freemarkerConfig", cfg );
//        
//        Template temp = orderAutoFlowService.getTemplate("orderAutoFlowRules.dslr.ftl");
//        assertNotNull(temp);
//    }
//
//    /**
//     * 由FreeMarker文件生成drools drl文件
//     * 场景：成功
//     */
//    @Test
//    public void generDslrFromFtlFile_Success() {
//        FreeMarkerConfigurer cfg = getFreeMarkerConfiguration();
//        ReflectionTestUtils.setField(orderAutoFlowService, "freemarkerConfig", cfg );
//        Template temp = orderAutoFlowService.getTemplate("orderAutoFlowRules.dslr.ftl");
//        UserRule userRule = createUserRule();
//        
//        String drl = orderAutoFlowService.generDslrFromFtlFile(userRule,temp);
//        assertNotNull(drl);
//        System.out.println(drl);
//    }
//    
//    @Test
//    public void getDrlFromDslTest_Success(){
//        FreeMarkerConfigurer cfg = getFreeMarkerConfiguration();
//        ReflectionTestUtils.setField(orderAutoFlowService, "freemarkerConfig", cfg );
//        Template temp = orderAutoFlowService.getTemplate("orderAutoFlowRules.ftl");
//        UserRule userRule = createUserRule();
//        
//        String dslr = orderAutoFlowService.generDslrFromFtlFile(userRule,temp);
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
//        String dslr = orderAutoFlowService.generDslrFromFtlFile(createUserRule(),temp);
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
//    private UserRule createUserRule(){
//        UserRule userRule = new UserRule();
//        //userRule.setUserCode("cef");
//        userRule.setRuleName("order flow");
//        userRule.getRuleConditionMap().put("expr_104", 50);
//        userRule.getRuleConditionMap().put("expr_107", "ebay");
//        List<String> destIncludes = new ArrayList<String>();
//        destIncludes.add("china");
//        destIncludes.add("brazil");
//        destIncludes.add("US");
//        userRule.getRuleConditionMap().put("expr_109", destIncludes);
//        
//        return userRule;
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
//        cfg.setTemplateLoaderPath("inte/drools");
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
//}
