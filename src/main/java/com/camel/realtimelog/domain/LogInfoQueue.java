/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.domain;

import java.io.InputStream;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * queue，存放读取到的log info。用于后续分析
 * @author dengqb
 * @date 2015年1月29日
 */
public final class LogInfoQueue {

    private static TransferQueue<InputStream> transferQueue = new LinkedTransferQueue<InputStream>();
    
    private LogInfoQueue(){
        
    }
    
    public static void put(InputStream inputStream) {
        try {
            transferQueue.put(inputStream);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static InputStream take() {
        try {
            return transferQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
