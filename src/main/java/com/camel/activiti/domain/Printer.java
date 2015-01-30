package com.camel.activiti.domain;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Printer {
    
    public void printMessage(){
        System.out.println(Thread.currentThread().getName() +" hello world");
    }
    
    public void printTimerMessage(){
        System.out.println(Thread.currentThread().getName() +" after timer waiting 5s, interrupte activity, change path to here");
    }
}
