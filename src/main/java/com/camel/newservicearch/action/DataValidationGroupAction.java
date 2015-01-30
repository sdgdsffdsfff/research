/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.newservicearch.action;

import javax.annotation.Resource;

import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Component;

import com.camel.newservicearch.aop.annotation.Execute;
import com.camel.newservicearch.domain.ActionResponse;
import com.camel.newservicearch.domain.Order;

/**
 * 操作group
 * @author dengqb
 * @date 2015年1月16日
 */
@Component
public class DataValidationGroupAction implements IBaseOrderAction {
    @Resource(name="SKUValidationAction")
    private IBaseOrderAction skuValidationAction;
    @Resource(name="addressValidationAction")
    private IBaseOrderAction addressValidationAction;
    
    @Override
    @Profiled
    @Execute
    public ActionResponse execute(final Order order, final ActionResponse actionResponse) {
        skuValidationAction.execute(order, actionResponse);
        addressValidationAction.execute(order, actionResponse);
        throw new RuntimeException("whatever I would failed you. huh");
        //return actionResponse;
    }

}
