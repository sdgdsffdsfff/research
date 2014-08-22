package mock.com.camel.drools.expert.sample.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import mockit.NonStrictExpectations;
import mockit.Tested;

import org.drools.compiler.rule.builder.RuleBuilder;
import org.drools.core.definitions.InternalKnowledgePackage;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.reteoo.RuleBuilderFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;

import com.camel.drools.expert.sample.domain.ERPCondition;
import com.camel.drools.expert.sample.domain.Item;
import com.camel.drools.expert.sample.service.EbayRelistingByRuleService;

/**
 * <pre>根据规则过滤自动下架的商品，找到符合规则需要重新刊登的商品，并且重新刊登</pre>
 * 
 * @author dengqb
 * @date 2014年8月18日
 */
public class EbayRelistingByRuleServiceMockTest {
    @Tested
    EbayRelistingByRuleService ebayRelistingByRuleService = new EbayRelistingByRuleService();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * 初始化kBase，添加规则库(Package)
     * 场景：成功
     */
    @Test
    public void initKBaseTest_Success() {
        InternalKnowledgeBase kBase = ebayRelistingByRuleService.initKBase();
        assertNotNull(kBase);
        
        String dslFile = "/com/camel/drools/expert/sample/findEbayRelistingItems.dsl";
        String dslrFile = "/com/camel/drools/expert/sample/findEbayRelistingItems.dslr";
        
        InternalKnowledgePackage pkg = ebayRelistingByRuleService.getRulePackage_1(dslFile, dslrFile);
        kBase.addPackage(pkg);
        assertEquals(1, pkg.getRules().size());
    }
    
    /**
     * 读取DSL和DSLR创建规则(Rule)和规则库(Package)
     * 场景：成功
     */
    @Test
    public void getRulePackageTest_1_Success() {
        String dslFile = "/com/camel/drools/expert/sample/findEbayRelistingItems.dsl";
        String dslrFile = "/com/camel/drools/expert/sample/findEbayRelistingItems.dslr";
        
        InternalKnowledgePackage pkg = ebayRelistingByRuleService.getRulePackage_1(dslFile, dslrFile);
        
        assertNotNull(pkg);
        
        assertEquals(1, pkg.getRules().size());
        
        System.out.println(pkg.getRule("ebay return policy").toString());
    }
    
    /**
     * 读取DSL和DSLR创建规则(Rule)和规则库(Package)，方法二
     * 场景：成功
     */
    @Test
    public void getRulePackageTest_2_Success() {
        String dslFile = "/com/camel/drools/expert/sample/findEbayRelistingItems.dsl";
        String dslrFile = "/com/camel/drools/expert/sample/findEbayRelistingItems.dslr";
        
        InternalKnowledgePackage pkg = ebayRelistingByRuleService.getRulePackage_2(dslFile, dslrFile);
        
        assertNotNull(pkg);
        
        assertEquals(1, pkg.getRules().size());
    }
    
    /**
     * 根据规则获取需要重新刊登的items
     * 场景：成功
     */
    @Test
    public void getEbayRelistingItemsByRuleTest_Success(){
        String dslFile = "/com/camel/drools/expert/sample/findEbayRelistingItems.dsl";
        String dslrFile = "/com/camel/drools/expert/sample/findEbayRelistingItems.dslr";
        
        InternalKnowledgePackage pkg = ebayRelistingByRuleService.getRulePackage_1(dslFile, dslrFile);
        
        InternalKnowledgeBase kBase = ebayRelistingByRuleService.initKBase();
        kBase.addPackage(pkg);
        
        Item item_1 = new Item("item_1",19.00, 51, "unlisting");
        Item item_2 = new Item("item_2",10.00, 10, "listing");
        Item item_3 = new Item("item_3",28.00, 50, "unlisting");
        
        List<Item> facts = new ArrayList();
        facts.add(item_1);
        facts.add(item_2);
        facts.add(item_3);
        
        //传入重新刊登的条件对象，如果满足这些条件则表示可以重新刊登
        ERPCondition cond = new ERPCondition(18.00,50,"unlisting");
        List<Item> itemLst = ebayRelistingByRuleService.getEbayRelistingItemsByRule(kBase,facts,cond);
        
        assertNotNull(itemLst);
        assertEquals(2,itemLst.size());
    }
    
    /**
     * 重新刊登items
     * 场景：成功
     */
    @Test
    public void relistingItemsTest_Success(){
        Item item_1 = new Item("item_1",19.00, 50, "unlisting");
        Item item_3 = new Item("item_3",28.00, 50, "unlisting");
        
        List<Item> itemLst = new ArrayList<Item>();
        itemLst.add(item_1);
        itemLst.add(item_3);
        
        final int count = ebayRelistingByRuleService.relistingItems(itemLst);
        assertEquals(2,count);
    }

}
