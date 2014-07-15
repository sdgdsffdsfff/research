/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.activemq.BASE.dao;

import org.springframework.stereotype.Repository;

import com.camel.activemq.BASE.domain.Price;
import com.camel.framework.dao.base.EntityDao4Mybatis;

/**
 * 
 * @author dengqb
 * @date 2014年4月30日
 */
@Repository
public class PriceDao extends EntityDao4Mybatis<Price, Long> {

}
