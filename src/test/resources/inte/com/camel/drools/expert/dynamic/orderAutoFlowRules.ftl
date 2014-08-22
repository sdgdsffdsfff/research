package com.camel.drools.expert.sample

import com.camel.drools.expert.sample.domain.Order;
import com.camel.drools.expert.sample.domain.Item;
import com.camel.drools.expert.sample.domain.OrderAutoFlowRule;

import function com.camel.drools.expert.sample.utils.DroolsOrderFunction.itemIdExistInCondition;

rule "${orderAutoFlowRule.ruleName}"
when
    <#if orderAutoFlowRule??>
    there is an orderAutoFlowRule with
    there is an order with
        <#list orderAutoFlowRule.ruleExpretions as ruleExp>
        ${ruleExp}
        </#list>
    </#if>
    
then
    there is out test
end