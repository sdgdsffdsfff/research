/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.activemq.BASE;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camel.activemq.BASE.dao.PriceDao;
import com.camel.activemq.BASE.domain.Price;

/**
 * 
 * @author dengqb
 * @date 2014年2月28日
 */
@Service
@Transactional
public class QueueServiceImpl implements IQueueService{
    @Autowired
    private JmsTemplate producerJmsTemplate;
    
    @Resource
    private PriceDao priceDao;
    
    private String testBaseQueueName = "test.base.queue";
    
    @Override
    public void sendMessage(final String message){
        producerJmsTemplate.send(testBaseQueueName, new DemoMessageCreator(message));
    };
    
    @Override
    @Transactional(rollbackFor=RuntimeException.class)
    public void transactionSendWithDB(final String msg1, final String msg2){
        Price price = new Price();
        price.setPrice(12.00D);
        price.setUserid(1);
        
        priceDao.save(price);
        //throw new RuntimeException("break down after save ！！");
        sendMessage(msg1);
        //sendMessage(msg2);
        sendMessageWithException(msg2);
    }
    
    private void sendMessageWithException (final String message){
        throw new RuntimeException("break down send msg : " + message);
        //producerJmsTemplate.send(testBaseQueueName, new DemoMessageCreator(message));
    };
    
    private class DemoMessageCreator implements MessageCreator {
        private String msg;
        public DemoMessageCreator(String message) {
            this.msg = message;
        }

        @Override
        public Message createMessage(Session session) throws JMSException {  
            TextMessage textMessage = session.createTextMessage(msg);
            return textMessage;  
        }  
    }
}
