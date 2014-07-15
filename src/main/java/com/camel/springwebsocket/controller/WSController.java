/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.springwebsocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * websocket controller
 * @author dengqb
 * @date 2014年4月2日
 */
@Controller
public class WSController {
    
    @RequestMapping(value="/wsdemo")
    public String goDemoPage(){
        return "websocket/websocket";
    }
}
