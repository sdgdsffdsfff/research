/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.newservicearch.action;

import com.camel.newservicearch.domain.ActionResponse;
import com.camel.newservicearch.domain.Order;

/**
 * 
 * @author dengqb
 * @date 2015年1月15日
 */
public interface IBaseOrderAction {
    
    public ActionResponse execute(final Order order, final ActionResponse actionResponse);
}
