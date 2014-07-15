/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package concurrent;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author dengqb
 * @date 2013年12月2日
 */
public class TestFutureTask {

    /**
     * @param args
     */
    public static void main(String[] args)throws InterruptedException,  
        ExecutionException {
        final ExecutorService exec = Executors.newFixedThreadPool(5);
        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                System.out.println("inside call method before " + Thread.currentThread().getName() + new Date());
                Thread.sleep(1000 * 1);// 休眠指定的时间，此处表示该操作比较耗时
                System.out.println("inside call method after " + Thread.currentThread().getName()+ new Date());
                return "Other less important but longtime things.";
            }
        };
        Future<String> task = exec.submit(call);
        Future<String> task1 = exec.submit(call);
        // 重要的事情
//        System.out.println(new Date());
//        
//        Thread.sleep(1000 * 3);
//        System.out.println(Thread.currentThread().getName());
//        System.out.println(new Date());
//        System.out.println("Let's do important things.");
//        // 不重要的事情
//        String obj = task.get();
//        System.out.println(obj);
//        // 关闭线程池
        exec.shutdown();
    }

}
