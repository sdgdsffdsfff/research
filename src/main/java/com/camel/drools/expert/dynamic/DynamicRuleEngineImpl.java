/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.dynamic;

import org.drools.compiler.lang.api.DescrFactory;
import org.drools.compiler.lang.api.PatternDescrBuilder;
import org.drools.compiler.lang.descr.AndDescr;
import org.drools.compiler.lang.descr.BindingDescr;
import org.drools.compiler.lang.descr.ExprConstraintDescr;
import org.drools.compiler.lang.descr.OrDescr;
import org.drools.compiler.lang.descr.PackageDescr;
import org.drools.compiler.lang.descr.PatternDescr;
import org.drools.compiler.lang.descr.RuleDescr;

import com.camel.drools.expert.Applicant;

/**
 * 规则引擎，支持动态程序创建，从drl文档读取。
 * 这是有状态的类，每个用户一份
 * @author dengqb
 * @date 2014年8月12日
 */
public class DynamicRuleEngineImpl implements IDynamicRuleEngine {
    
//    @Override
//    public RuleBase getRuleBase(String userCode) {
//        return UserRules.getRuleBase(userCode);
//    }

    @Override
    public void dynamictRuleCreate() {
        
    }

}
