package mock.com.camel.drools.expert.sample.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mockit.Tested;

import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.impl.InternalKnowledgeBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.definition.KiePackage;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;
import org.kie.internal.KnowledgeBaseFactory;
import org.springframework.test.util.ReflectionTestUtils;

import com.camel.drools.expert.sample.domain.ERPCondition;
import com.camel.drools.expert.sample.domain.Item;
import com.camel.drools.expert.sample.domain.Order;
import com.camel.drools.expert.sample.domain.UserRule;
import com.camel.drools.expert.sample.domain.UserRuleCondition;
import com.camel.drools.expert.sample.service.EbayRelistingByRuleService;
import com.camel.drools.expert.sample.service.KBaseContext;
import com.camel.drools.expert.sample.service.OrderFlowControlService;
import com.camel.drools.expert.sample.utils.RegisterSDOXsd;

public class OrderFlowControlServiceMockTest {
    @Tested
    OrderFlowControlService orderFlowControlService = new OrderFlowControlService();
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindEbayRelistingItem() {
        InternalKnowledgeBase kBase = KBaseContext.getKBaseInstance();
//        String shimDrlFile = "/drools/drl/shimDrl.drl";
//        loadPackageFromDrl(kBase, shimDrlFile);
        
        String drlFile = "/drools/drl/findEbayRelistingItems_1.drl";
        loadPackageFromDrl(kBase, drlFile);
        
//        Item item_1 = new Item("item_1",19.00, 51, null);
        Item item_1 = new Item();
        item_1.setPrice(19.00);
        item_1.setStatus("unlisting");
        Item item_2 = new Item("item_2",10.00, 10, "listing");
//        Item item_3 = new Item("item_3",28.00, 50, null);
        Item item_3 = new Item();
        item_3.setPrice(28.00);
        item_3.setStatus("unlisting");
        
//        List<Item> facts = new ArrayList();
//        facts.add(item_1);
//        facts.add(item_2);
//        facts.add(item_3);
        
        //传入重新刊登的条件对象，如果满足这些条件则表示可以重新刊登
        //ERPCondition cond = new ERPCondition(18.00,50,"unlisting");
        ERPCondition cond = new ERPCondition();
        cond.setPrice(18.00);
        cond.setSaledNum(50);
        cond.setStatus("unlisting");
        
        List<Item> itemLst = excecuteRules(kBase, item_1, cond);
        
        Iterator<KiePackage> it = kBase.getKiePackages().iterator();
        while (it.hasNext()){
            kBase.removeKiePackage(it.next().getName());
        }
        
        //添加新规则
        System.out.println("========add new rule=================");
        String drlFile_2 = "/drools/drl/findEbayRelistingItems_2.drl";
        loadPackageFromDrl(kBase, drlFile_2);
        
        itemLst = excecuteRules(kBase,item_3,cond);
      //一条规则，执行多次
//        for (int i=1; i<=2; i++){
//        }
    }

    @Test
    public void testFindOrder(){
        //InternalKnowledgeBase kBase = KBaseContext.getKBaseInstance();
        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        String drlFile = "/drools/drl/order1.drl";
        loadPackageFromDrl(kBase, drlFile);
//        String drlFile2 = "/drools/drl/order2.drl";
//        loadPackageFromDrl(kBase, drlFile2);
        
        //注册sdo对象
        RegisterSDOXsd register = new RegisterSDOXsd();
        register.setXsdFilePath("/drools/xsd/xsdList.txt");
        register.register();
        
        String rcJson = "{\"expr_104\":50,\"expr_107\":\"ebay\",\"expr_109\":[\"china\",\"brazil\",\"US\"]}";
        Order order = new Order(50,"ebay","china");
        Order order2 = new Order(50.0,"ebay","brazil");
        
//        //传入重新刊登的条件对象，如果满足这些条件则表示可以重新刊登
        
//        UserRule userRule = new UserRule();
//        userRule.setUserCode("order1");
//        userRule.loadRuleConditionFromJson(rcJson);
//        
//        excecuteOrderRule(kBase,order, userRule);
        
        System.out.println("========add new rule=================");
        //添加新的规则
//        String drlFile2 = "/drools/drl/order2.drl";
        String drlFile2 = "/drools/drl/order2.drl";
        loadPackageFromDrl(kBase, drlFile2);
        
        UserRule userRule2 = new UserRule();
        userRule2.setUserCode("order2");
        userRule2.loadRuleConditionFromJson(rcJson);
        //执行新规则
        excecuteOrderRule(kBase,order2,userRule2);
    }
    
    @Test
    public void testDynamicAddRuleBug(){
        InternalKnowledgeBase kBase = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
        String drlFile = "/drools/drl/order1.drl";
        loadPackageFromDrl(kBase, drlFile);
        
        //注册sdo对象
        RegisterSDOXsd register = new RegisterSDOXsd();
        register.setXsdFilePath("/drools/xsd/xsdList.txt");
        register.register();
        
        String rcJson = "{\"expr_104\":50,\"expr_107\":\"ebay\",\"expr_109\":[\"china\",\"brazil\",\"US\"]}";
        Order order = new Order(50,"ebay","china");
        Order order2 = new Order(50.0,"ebay","brazil");
        
//        //传入重新刊登的条件对象，如果满足这些条件则表示可以重新刊登
        
        UserRule userRule = new UserRule();
        userRule.setUserCode("order");
        userRule.loadRuleConditionFromJson(rcJson);
        
        excecuteOrderRule(kBase,order, userRule);
        
        System.out.println("========add new rule=================");
        //添加新的规则
//        String drlFile2 = "/drools/drl/order2.drl";
        String drlFile2 = "/drools/drl/order2.drl";
        loadPackageFromDrl(kBase, drlFile2);
        
        UserRule userRule2 = new UserRule();
        userRule2.setUserCode("order2");
        userRule2.loadRuleConditionFromJson(rcJson);
        //执行新规则
        excecuteOrderRule(kBase,order2,userRule2);
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

    private List<Item> excecuteRules(InternalKnowledgeBase kBase, Item fact, ERPCondition cond) {
        KieSession kieSession = kBase.newStatefulKnowledgeSession();
        
        List<Item> relistingItems = new ArrayList<Item>();
        kieSession.setGlobal("relistingItems", relistingItems);
        
        kieSession.insert(fact);
        kieSession.insert(cond);
        kieSession.fireAllRules();
        kieSession.dispose();
        return relistingItems;
    }

    private List<Order> excecuteOrderRule (InternalKnowledgeBase kBase,Order order,final UserRule userRule){
        KieSession kieSession = kBase.newStatefulKnowledgeSession();
        
        List<Order> matchOrders = new ArrayList<Order>();
        kieSession.setGlobal("matchOrders", matchOrders);
        
        kieSession.insert(userRule);
        kieSession.insert(order);
        kieSession.fireAllRules(new AgendaFilter(){
            @Override
            public boolean accept(Match match) {
                return match.getRule().getPackageName().endsWith(userRule.getUserCode());
            }
        });
        
        //statfull session使用后必需调用dispose
        kieSession.dispose();
        return matchOrders;
    }
    
    private List<Order> excecuteOrderRule (InternalKnowledgeBase kBase,Order order,ERPCondition cond){
        KieSession kieSession = kBase.newStatefulKnowledgeSession();
        
        List<Order> matchOrders = new ArrayList<Order>();
        kieSession.setGlobal("matchOrders", matchOrders);
        
        kieSession.insert(order);
        kieSession.insert(cond);
//        kieSession.fireAllRules(new AgendaFilter(){
//            @Override
//            public boolean accept(Match match) {
//                return match.getRule().getPackageName().endsWith(userRule.getUserCode());
//            }
//        });
        kieSession.fireAllRules();
        //statfull session使用后必需调用dispose
        kieSession.dispose();
        return matchOrders;
    }
    
}
