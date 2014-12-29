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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.camel.utils.FileUtils;
import com.camel.utils.MD5;

/**
 * 
 * @author dengqb
 * @date 2014年12月24日
 */
public class TicketReserver {
    private static String cookieStr = "JSESSIONID=0CFEA1651D46BE37210061CF5A488A9A; _jc_save_showZtkyts=true; BIGipServerotn=133169674.38945.0000; _jc_save_fromStation=深圳,SZQ; _jc_save_toStation=邵阳,SYQ; _jc_save_fromDate=2015-01-25; _jc_save_toDate=2014-12-24; _jc_save_wfdc_flag=dc; current_captcha_type=C";
    
    private static String repeat_submit_token="011cb0f29a9a2d7050fc8c1875fe40fe";
    
    public static String checkOrderUrl = "https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo";
    
    public static List<NameValuePair> datas = new ArrayList<NameValuePair>();
    
    private static void login(){
        
    }
    /**
     * setp 1
     * 1:query
     */
    private static void query(){
        
    }
    
    /**
     * setp 2
     * 1: initDc
     */
    private static void clickBooking(){
        
    }
    
    /**
     * setp 3
     * 1: checkOrderInfo
     * 2: getQueueCount
     */
    private static void confirmPassenger(){
        
    }
    
    private static void getCaptchaImg(){
        String url = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp&0.20558934959469144";
        ByteArrayOutputStream outStream = JDKHttpsClient.doGetImg(url,cookieStr);
        if (outStream.size() > 0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            File imageFile = new File("G://12306OCR-2//" + (new Date()).getTime()+".png");
            try {
                FileOutputStream fos = new FileOutputStream(imageFile);
                byte[] bytes = outStream.toByteArray();
                fos.write(bytes);
                fos.flush();
                fos.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(Thread.currentThread().getName() + " return empty");
        }
    }
    
    public static void main (String[] args){
        getCaptchaImg();
        
        String filePath = FileUtils.getFileAbsolutePath();
        Properties props = FileUtils.readProperties(filePath + "/trainreserve/checkorderInfo.properties");
        Iterator it = props.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();
            NameValuePair nvp = new BasicNameValuePair(key,(String) props.get(key));
            datas.add(nvp);
        }
        String formDate = URLEncodedUtils.format(datas, "UTF-8");
        String res = null;
        try {
            res = JDKHttpsClient.doPost(checkOrderUrl, cookieStr, formDate, "UTF-8", 3000, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("response ="+res);
    }
}
