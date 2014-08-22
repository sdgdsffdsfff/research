/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.dynamic;

/**
 * 规则引擎，支持动态程序创建，从drl文档读取。
 * @author dengqb
 * @date 2014年8月12日
 */
public interface IDynamicRuleEngine {

    /**
     * 动态创建规则
     */
    void dynamictRuleCreate();
    
    /**
     * 获取指定用户的RuleBase
     * @return 用户的规则库
     */
//    RuleBase getRuleBase(String userCode);
}
