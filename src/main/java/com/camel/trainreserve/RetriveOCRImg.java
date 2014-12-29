/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.trainreserve;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.tools.ant.taskdefs.Sleep;

import com.camel.utils.MD5;

/**
 * 获取预订提交验证码
 * @author dengqb
 * @date 2014年12月23日
 */
public class RetriveOCRImg {
    private static Map<String,String> md5Map = new HashMap<String,String>();
    
    private static String cookieStr = "JSESSIONID=82257E1482E2C0ED38397A7D7C59A112; _jc_save_showZtkyts=true; BIGipServerotn=351273482.38945.0000; _jc_save_fromStation=%u6DF1%u5733%2COSQ; _jc_save_toStation=%u90B5%u9633%2CSYQ; _jc_save_fromDate=2015-01-25; _jc_save_toDate=2014-12-24; _jc_save_wfdc_flag=dc; current_captcha_type=C";

    private static void sheduleJob(ScheduledExecutorService exec, final String url){
        exec.scheduleWithFixedDelay(new Runnable(){
            public void run(){
                ByteArrayOutputStream outStream = JDKHttpsClient.doGetImg(url,cookieStr);
                if (outStream.size() > 0){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    File imageFile = new File("G://12306OCR-1//" + (new Date()).getTime()+".png");
                    try {
                        FileOutputStream fos = new FileOutputStream(imageFile);
                        byte[] bytes = outStream.toByteArray();
                        fos.write(bytes);
                        String md5Str = MD5.GetMD5Code(bytes);
                        if (md5Map.containsKey(md5Str)){
                            String existedFile = md5Map.get(md5Str);
                            System.out.println("md5 repeated: map size=" + md5Map.size() + "### md5 same with " + existedFile);
                        }
                        md5Map.put(md5Str, imageFile.getName());
                        System.out.println(Thread.currentThread().getName()+ ":md5= "+md5Str+". map size = "+ md5Map.size());
                        fos.close();
                        outStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println(Thread.currentThread().getName() + " return empty");
                }
            }
        },0, 2, TimeUnit.SECONDS);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        String url = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp&0.11995659543649284";
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(5);
        for (int i=0; i<5 ; i++ ) {
            sheduleJob(exec,url);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
