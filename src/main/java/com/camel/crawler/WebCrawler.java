/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.crawler;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.camel.utils.FileUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 网页爬虫
 * @author dengqb
 * @date 2015年3月13日
 */
public class WebCrawler {
    private final OkHttpClient client;
    
    private final static String URL_PRE = "http://www.88152.com/shop/";
//    private static int pageInit = 1851220;
//    private static int pageEnd = 1851226;
    private static int pageInit = 1845289;
    private static int pageEnd = 1937300;
    
    private File emailFile; 
    private Pattern emailPattern;
    
    private final String regex = "(<li>电子邮箱：)(.*)(</li>)";
    
    private ArrayBlockingQueue queue;
    
    public WebCrawler () {
        client = new OkHttpClient();
        emailPattern = Pattern.compile(regex);
        emailFile = new File("emailout.txt");
    }
    
    public void fetchWeb (String url ) throws IOException {
        client.setConnectTimeout(2, TimeUnit.SECONDS);
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        
        int responseCode = response.code();
        
        if (responseCode == 200){
            extraInfo(response.body().string());
        }else{
            System.out.println("got error page");
        }
    }
    
    /**
     * 提出邮件信息
     * @param webpage
     */
    public void extraInfo (String webpage) {
        Matcher matcher = emailPattern.matcher(webpage);
        if(matcher.find()){
            String email = matcher.group(2);
            if (!StringUtils.isEmpty(email)){
                System.out.println("find email="+ email);
                FileUtils.writeAppendFile(emailFile, email.trim()+System.lineSeparator());
            }else{
                System.out.println("empty email address");
            }
        }
    }
    
    public static void main (String[] args) {
        WebCrawler crawler = new WebCrawler();
        
        boolean finished = true;
        
        while(finished){
            try {
                pageInit ++;
                System.out.println("url num:="+ pageInit);
                try {
                    crawler.fetchWeb(URL_PRE + String.valueOf(pageInit) + "/");
                } catch (IOException e) {
                    pageInit --;
                    e.printStackTrace();
                    //出现网络异常，睡眠1分钟后，继续
                    try {
                        System.out.println("exception sleep 1 min");
                        Thread.sleep(60000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    } 
                }
                //页面抓取完成，结束crawler
                if (pageInit == pageEnd){
                    finished = false;
                }
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }catch (Exception e) {
                System.out.println("unknow exception");
                e.printStackTrace();
            }
        }
    }
}
