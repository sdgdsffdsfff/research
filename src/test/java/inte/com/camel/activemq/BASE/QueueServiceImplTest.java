/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package inte.com.camel.activemq.BASE;

import inte.com.AbstractTransactionalSpringBaseTest;
import inte.com.BaseTest;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.camel.activemq.BASE.IQueueService;
import com.camel.framework.utils.DateUtils;

/**
 * 
 * @author dengqb
 * @date 2014年4月26日
 */
public class QueueServiceImplTest extends AbstractTransactionalSpringBaseTest {
    
    @Autowired
    private IQueueService queueServiceImpl;
    
    @Test
    public void testSendMessage() {
        Date start = new Date();
        String msgBody = "update price from 2 to 5";
        queueServiceImpl.sendMessage(msgBody);
        System.out.println("start time:"+DateUtils.getStandardDate(start));
        System.out.println("end time:"+DateUtils.getStandardDate(new Date()));
    }
    
    @Test
    @Transactional
    public void testTransactionSendWithDB() {
        Date start = new Date();
        String msgBody1 = "update local user price from 2 to 5";
        String msgBody2 = "update remote user price from 2 to 5";
        queueServiceImpl.transactionSendWithDB(msgBody1, msgBody2);
        System.out.println("start time:"+DateUtils.getStandardDate(start));
        System.out.println("end time:"+DateUtils.getStandardDate(new Date()));
    }
}
