package com.camel.activemq.camel.business;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

@Component
public class DemoBusinessListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("message recieved ="+ message.toString());
        throw new RuntimeException();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("should not ack 1");
//        System.out.println("should not ack 2");
//        System.out.println("should not ack 3");
//        System.out.println("should not ack 4");
//        System.out.println("should not ack 5");
        
    }

}
