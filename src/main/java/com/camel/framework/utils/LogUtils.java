/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.framework.utils;

import java.util.Date;

/**
 * 日志工具
 * @author dengqb
 * @date 2014年8月19日
 */
public class LogUtils {
    private Date start;
    private Date end;

    public void beforeRun (String message){
        start = new Date();
        System.out.println(message + " start at:" + start);
    }
    
    public void afterRun(String message){
        end = new Date();
        System.out.println(message + " end at:" + end);
        System.out.println("time interval :" + getTimeInterval(start,end) + " 秒");
    }
    
    /**
     * 获取时间间隔
     * @param strat 开始时间
     * @param end 结束时间
     * @return
     */
    private String getTimeInterval(Date strat, Date end){
        long milisecconds = end.getTime() - start.getTime();
        double rs = milisecconds * 0.001;
        return String.valueOf(rs);
    }
}
