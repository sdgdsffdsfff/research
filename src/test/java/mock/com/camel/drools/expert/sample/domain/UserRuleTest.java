package mock.com.camel.drools.expert.sample.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.camel.drools.expert.sample.domain.UserRule;
import com.camel.drools.expert.sample.utils.RegisterSDOXsd;

import commonj.sdo.DataObject;

public class UserRuleTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testConvertUserRuleTOJson() {
        RegisterSDOXsd rsx = new RegisterSDOXsd();
        ReflectionTestUtils.setField(rsx, "xsdFilePath", "/drools/xsd/xsdList.txt");
        rsx.register();
        
        UserRule userRule = new UserRule();
        userRule.setUserCode("abc");
        userRule.setRuleName("order flow");
        userRule.getRuleConditionMap().put("expr_104", 50);
        userRule.getRuleConditionMap().put("expr_107", "ebay");
        List<String> destIncludes = new ArrayList<String>();
        destIncludes.add("china");
        destIncludes.add("brazil");
        destIncludes.add("US");
        userRule.getRuleConditionMap().put("expr_109", destIncludes);
        
        String json = userRule.convertUserRuleTOJson();
        
        assertNotNull(json);
        System.out.println(json);
    }

    @Test
    public void testLoadRuleConditionFromJson() {
        RegisterSDOXsd rsx = new RegisterSDOXsd();
        ReflectionTestUtils.setField(rsx, "xsdFilePath", "/drools/xsd/xsdList.txt");
        rsx.register();
        
        UserRule userRule = new UserRule();
        String json = "{\"expr_104\":50,\"expr_107\":\"ebay\",\"expr_109\":[\"china\",\"brazil\",\"US\"]}";
        userRule.loadRuleConditionFromJson(json);
        DataObject ruleCondition = userRule.getRuleCondition();
        assertNotNull(ruleCondition);
        assertEquals(50.0,ruleCondition.getDouble("amount"),0);
    }

}
