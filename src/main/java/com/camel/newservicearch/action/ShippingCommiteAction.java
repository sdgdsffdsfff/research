/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.newservicearch.action;

import org.springframework.stereotype.Component;

import com.camel.newservicearch.aop.annotation.Execute;
import com.camel.newservicearch.domain.ActionResponse;
import com.camel.newservicearch.domain.ErrorInfo;
import com.camel.newservicearch.domain.Order;
import com.camel.newservicearch.domain.OrderPackage;

/**
 * 预报功能
 * @author dengqb
 * @date 2015年1月14日
 */
@Component
public class ShippingCommiteAction implements IBaseOrderAction {

    /**
     * 提交预报
     * @param order
     * @param actionResponse
     * @return
     */
    @Execute
    @Override
    public ActionResponse execute(final Order order, final ActionResponse actionResponse) {
        if (order.getPkgs() == null || order.getPkgs().size() == 0){
            ErrorInfo error = new ErrorInfo();
            error.setErrorMessage("no order package found!");
            actionResponse.addError(error);
            return actionResponse;
        }
        
        for (OrderPackage op:order.getPkgs()){
            op.setDeliveryCode("DO-"+op.getPkgCD());
        }
        
        return actionResponse;
    }
}
