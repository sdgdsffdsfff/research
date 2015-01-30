/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.newservicearch.action;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.camel.newservicearch.aop.annotation.Execute;
import com.camel.newservicearch.domain.ActionResponse;
import com.camel.newservicearch.domain.ErrorInfo;
import com.camel.newservicearch.domain.Order;

/**
 * 地址校验功能
 * @author dengqb
 * @date 2015年1月14日
 */
@Component
public class AddressValidationAction implements IBaseOrderAction {

    @Override
    @Execute
    public ActionResponse execute(Order order, ActionResponse actionResponse) {
        if (StringUtils.isEmpty(order.getAddress().trim())){
            ErrorInfo error = new ErrorInfo();
            error.setErrorMessage("address canot be empty!");
            actionResponse.addError(error);
        }
        
        return actionResponse;
    }
}
