package com.camel.drools.expert.sample.${userRule.userCode}

import com.camel.drools.expert.sample.domain.Order;
import com.camel.drools.expert.sample.domain.Item;
import com.camel.drools.expert.sample.domain.UserRule;

import function com.camel.drools.expert.sample.utils.DroolsOrderFunction.itemIdExistInCondition;

rule "${userRule.ruleName}"
when
    <#if userRule??>
    there is an userRule with
        <#if userRule.hasTypeof("1")>
    there is an order with
            <#list userRule.ruleConditionMap?keys as key>
    - ${key}
            </#list>
        </#if>
    </#if>
    
then
    there is out test
end