/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.utils;

import java.util.List;

import com.camel.drools.expert.sample.domain.Item;

/**
 * DRL中会使用到的function
 * @author dengqb
 * @date 2014年8月22日
 */
public class DroolsOrderFunction {
    
    /**
     * 订单item的id最少有一个符合条件
     * @param items
     * @param conditions
     * @return
     */
    public static boolean itemIdExistInCondition (List<Item> items, List<Integer> conditions){
        for(Item item:items){
            if (conditions.contains(item.getId())){
                return true;
            }
        }
        return false;
    }
}
