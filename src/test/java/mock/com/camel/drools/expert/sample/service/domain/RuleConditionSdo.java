/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package mock.com.camel.drools.expert.sample.service.domain;

import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;

/**
 * 
 * @author dengqb
 * @date 2014年9月19日
 */
public class RuleConditionSdo {
    private DataObject ruleCondition;
    
    public RuleConditionSdo(){
        ruleCondition = DataFactory.INSTANCE.create("http://drools.research.com/xsd/RuleCondition", "RuleCondition");
    }

    public DataObject getRuleCondition() {
        return ruleCondition;
    }

    public void setRuleCondition(DataObject ruleCondition) {
        this.ruleCondition = ruleCondition;
    }
}
