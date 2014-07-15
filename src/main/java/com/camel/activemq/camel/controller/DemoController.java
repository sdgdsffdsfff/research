package com.camel.activemq.camel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.camel.activemq.camel.business.IDemoBusiness;

@Controller
@RequestMapping("/amq")
public class DemoController {
    @Autowired
    private IDemoBusiness demoBusiness;
    
    @RequestMapping(value="/queue", method=RequestMethod.PUT)
    public ModelAndView putMessage(){
        demoBusiness.sendMessage("aaa");
        return null;
    }
}
