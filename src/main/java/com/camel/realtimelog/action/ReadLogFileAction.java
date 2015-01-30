/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.action;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

import org.apache.log4j.Logger;

import com.camel.realtimelog.domain.LogInfoQueue;
import com.camel.realtimelog.domain.Logmark;
import com.camel.utils.ByteUtils;

/**
 * 读取log文件
 * @author dengqb
 * @date 2015年1月26日
 */
public class ReadLogFileAction implements IBaseAction {
    private final static Logger logger = Logger.getLogger(ReadLogFileAction.class);
    
    public InputStream readLogFile(final Logmark logmark){
        System.out.println("read log file");
        StringBuffer sb = new StringBuffer("here are log file");
        RandomAccessFile randomFile = null;
        try {
            randomFile = new RandomAccessFile(logmark.getLogFileAbsolutePath(), "r");
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            logger.error("FileNotFoundException", e1);
        }
        if (randomFile == null) {
            logger.error("RandomAccessFile is null");
            return null;
        }

        InputStream ins = null;
        try {
            if (randomFile.length() == logmark.getLastReadSize()) {
                // 相等表示日志无变化，不需要处理
                logger.debug(logmark.getLogFileAbsolutePath() + " log file has not changed");
                //修复open too many file 问题
                randomFile.close();
                return null;
            } else if (randomFile.length() < logmark.getLastReadSize()) {
                // 文件大小小于上次读取的大小，表示异常，重新开始读取
                logmark.setLastReadSize(0L);
            }
            // 获得变化部分的
            FileChannel fc = randomFile.getChannel();
            long size = fc.size();
            long position = logmark.getLastReadSize();
            // 新读取日志字节长度
            long comingSize = size - position;
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, position, comingSize);
            byte[] dst = new byte[(int) comingSize];
            mbb.get(dst);
            ins = new ByteArrayInputStream(dst);
            
            logmark.setLastReadSize(size);
            fc.close();
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ins;
    }
}
