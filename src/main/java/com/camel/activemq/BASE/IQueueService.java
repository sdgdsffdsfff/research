/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.activemq.BASE;

/**
 * 
 * @author dengqb
 * @date 2014年2月28日
 */
public interface IQueueService {

    public abstract void sendMessage(final String message);

    public abstract void transactionSendWithDB(final String msg1, final String msg2);

}
