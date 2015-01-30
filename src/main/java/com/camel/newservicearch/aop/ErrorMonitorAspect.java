/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.newservicearch.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.camel.newservicearch.domain.ActionResponse;

/**
 * 
 * @author dengqb
 * @date 2015年1月15日
 */
@Component
@Aspect
public class ErrorMonitorAspect {
    
    //@Pointcut("execution(* com.camel.newservicearch.action.*.execute(..))")
    @Pointcut("@annotation(com.camel.newservicearch.aop.annotation.Execute)")
    private void actionExecution(){};
    
    //returning参数获取方法返回对象
    @AfterReturning(pointcut = "actionExecution()", returning = "_result")
    public void doAfter(Object _result){
        ActionResponse ar = (ActionResponse)_result;
        System.out.println("aspect invoked");
        if (ar.getErrors().size() == 0){
            System.out.println("no errors");
        }else{
            System.out.println("there are "+ar.getErrors().size()+" errors!");
            //throw new RuntimeException("there are errors");
        }
    }
}
