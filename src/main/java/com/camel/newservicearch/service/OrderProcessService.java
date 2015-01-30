/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.newservicearch.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.camel.newservicearch.action.AddressValidationAction;
import com.camel.newservicearch.action.DataValidationGroupAction;
import com.camel.newservicearch.action.IBaseOrderAction;
import com.camel.newservicearch.action.PackageGenerationAction;
import com.camel.newservicearch.action.SKUValidationAction;
import com.camel.newservicearch.action.ShippingCommiteAction;
import com.camel.newservicearch.domain.ActionResponse;
import com.camel.newservicearch.domain.Order;

/**
 * 订单处理服务
 * @author dengqb
 * @date 2015年1月15日
 */
@Service
public class OrderProcessService {
    @Resource(name="dataValidationGroupAction")
    private IBaseOrderAction dataValidationGroupAction;
    @Resource(name="shippingCommiteAction")
    private IBaseOrderAction shippingCommiteAction;
    @Resource(name="packageGenerationAction")
    private IBaseOrderAction packageGenerationAction;


    public void orderProcess(final Order order){
        ActionResponse ar = new ActionResponse();
        try{
            dataValidationGroupAction.execute(order, ar);
            packageGenerationAction.execute(order, ar);
            shippingCommiteAction.execute(order, ar);
        }catch(Exception e){
            System.out.println("order process catch errors");
            e.printStackTrace();
        }
    }
}
