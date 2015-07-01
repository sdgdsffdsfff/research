/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.newservicearch.controlflow;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.camel.newservicearch.action.IBaseOrderAction;
import com.camel.newservicearch.domain.ActionResponse;
import com.camel.newservicearch.domain.Order;

/**
 * 整个活动控制流程，用于替代OrderProcessService
 * @author dengqb
 * @date 2015年2月9日
 */
@Component
public class ControlFlow {
    @Resource(name="dataValidationActivity")
    private IBaseOrderAction dataValidationActivity;
    @Resource(name="shippingCommiteAction")
    private IBaseOrderAction shippingCommiteAction;
    @Resource(name="packageGenerationAction")
    private IBaseOrderAction packageGenerationAction;


    public void orderProcess(final Order order){
        ActionResponse ar = new ActionResponse();
        try{
            dataValidationActivity.execute(order, ar);
            packageGenerationAction.execute(order, ar);
            shippingCommiteAction.execute(order, ar);
        }catch(Exception e){
            System.out.println("order process catch errors");
            e.printStackTrace();
        }
    }
}
