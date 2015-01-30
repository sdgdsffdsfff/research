/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.newservicearch.action;

import org.springframework.stereotype.Component;

import com.camel.newservicearch.aop.annotation.Execute;
import com.camel.newservicearch.domain.ActionResponse;
import com.camel.newservicearch.domain.Order;
import com.camel.newservicearch.domain.OrderPackage;

/**
 * 生成包裹功能
 * @author dengqb
 * @date 2015年1月14日
 */
@Component
public class PackageGenerationAction implements IBaseOrderAction {

    /**
     * 生成包裹
     * @param order
     * @param actionResponse
     * @return actionResponse
     */
    @Override
    @Execute
    public ActionResponse execute(Order order, ActionResponse actionResponse) {
        OrderPackage op1 = generateOrderPackage(order);
        op1.setPkgCD("P1-"+order.getOrderCD());
        order.addPkg(op1);
        if (order.getSkuNum() > 1){
            op1.setSkuNum(order.getSkuNum()/2);
            
            OrderPackage op2 = generateOrderPackage(order);
            op2.setSkuNum(order.getSkuNum()/2);
            op2.setPkgCD("P2-"+order.getOrderCD());
            order.addPkg(op2);
        }else{
            op1.setSkuNum(order.getSkuNum());
        }
        
        return actionResponse;
    }
    
    private OrderPackage generateOrderPackage(final Order order){
        OrderPackage op = new OrderPackage();
        op.setOrderCD(order.getOrderCD());
        op.setSkuCode(order.getSkuCode());
        return op;
    }

}
