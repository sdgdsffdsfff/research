package com.camel.activemq.camel.business;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DemoBusiness implements IDemoBusiness {
    private static final Logger logger = Logger.getLogger(DemoBusiness.class);
    
    @Autowired(required=false)
    private JmsTemplate producerJmsTemplate;
    @Autowired(required=false)
    private Destination testOrderQueue_1;
    @Autowired(required=false)
    private Destination testOrderQueue_2;
    @Autowired(required=false)
    private Destination testOrderQueue_3;
    /**
     * 测试聚石塔生产服务wmc订单测试队列
     */
//    @Autowired
//    private Destination tbTestWmcOrderQueue;
    
    /* (non-Javadoc)
     * @see com.camel.activemq.camel.business.IDemoBusiness#sendMessage(java.lang.String)
     */
    @Override
    public void sendMessage(final String message) {
        logger.info("Send message: " + message); 
        //订单信息同时发生给cec和wmc队列
        //jmsTemplate.send(new DemoMessageCreator(message));  
        producerJmsTemplate.send(testOrderQueue_1, new DemoMessageCreator(message));
        producerJmsTemplate.send(testOrderQueue_2, new DemoMessageCreator(message));
        producerJmsTemplate.send(testOrderQueue_3, new DemoMessageCreator(message));
    }
    
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
