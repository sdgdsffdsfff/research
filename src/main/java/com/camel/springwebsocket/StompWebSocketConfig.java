/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.springwebsocket;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * 映射stomp请求到broker 目的地
 * @author dengqb
 * @date 2014年4月2日
 */
//@Configuration
//@EnableWebSocket
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    /**
     * 启动简单broker，2个队列queue和topic
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/stomp")
            .enableSimpleBroker("/queue","/topic");
    }
    
    /**
     * 匹配请求路径和websocket server。
     * /app/portfolio的请求会指向这里
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/portfolio");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration arg0) {
        // TODO Auto-generated method stub
        
    }


}
