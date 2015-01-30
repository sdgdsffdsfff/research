/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.test;

import com.camel.realtimelog.service.RealtimeLogService;

/**
 * 
 * @author dengqb
 * @date 2015年1月22日
 */
public class MainTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        RealtimeLogService realtimeLogService = new RealtimeLogService();
        realtimeLogService.execute();
        
        System.out.println(realtimeLogService.getConfig().getLogFileConfigs().size());
        System.out.println(realtimeLogService.getLogmarkFile().getLogmarks().size());
    }

}
