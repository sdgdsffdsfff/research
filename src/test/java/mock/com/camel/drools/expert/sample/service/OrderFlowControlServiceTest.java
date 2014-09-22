package mock.com.camel.drools.expert.sample.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.drools.compiler.builder.impl.KnowledgeBuilderImpl;
import org.drools.compiler.compiler.DroolsParserException;
import org.drools.core.impl.InternalKnowledgeBase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

import com.camel.drools.expert.sample.domain.ERPCondition;
import com.camel.drools.expert.sample.domain.Item;
import com.camel.drools.expert.sample.service.KBaseContext;

public class OrderFlowControlServiceTest {

    @Test
    public void testFindEbayRelistingItem() {
        InternalKnowledgeBase kBase = KBaseContext.getKBaseInstance();
        String drlFile = "/drools/drl/findEbayRelistingItems_1.drl";
        loadPackageFromDrl(kBase, drlFile);
        String drlFile_2 = "/drools/drl/findEbayRelistingItems_2.drl";
        loadPackageFromDrl(kBase, drlFile_2);
        
        Item item_1 = new Item("item_1",19.00, 51, "unlisting");
        Item item_3 = new Item("item_3",28.00, 50, "unlisting");
        
        ERPCondition cond = new ERPCondition(18.00,50,"unlisting");
        
        List<Item> itemLst = excecuteRules(kBase, item_1, cond);
        itemLst = excecuteRules(kBase,item_3,cond);
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
}
