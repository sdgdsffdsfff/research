/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * 文件唯一性识别
 * @author dengqb
 * @date 2014年11月7日
 */
public class FileIdentification {
    private static final Logger logger = Logger.getLogger(FileIdentification.class);
    private static String OS = "";
    private static Runtime rt = Runtime.getRuntime();
    
    /**
     * 获取文件的唯一值，
     * linux：获取文件的indo值
     * windows：获取文件的创建日期
     * @param absoluteLogFilePath 文件的绝对路径
     * @return
     */
    public static String getFileIdentity (String absoluteLogFilePath) {
        getOS();
        if (OS.toLowerCase().contains("windows")){
            String result = runWindownCMD(absoluteLogFilePath);
            return result;
        }else if (OS.toLowerCase().contains("linux")){
            String result = runShellCMD(absoluteLogFilePath);
            return result.split(" ")[0];
        }
        return "no file id";
    }
    
    //获取操作系统
    private static String getOS(){
        if (OS == null || OS.equals("")){
            OS = System.getProperty("os.name");
            logger.debug(OS);
        }
        return OS;
    }
    
    private static String runShellCMD(String logFilePath){
        Process pcs = null;
        try {
            File file = new File(logFilePath);
            file.getParent();
            pcs = rt.exec("ls -i "+logFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 定义shell返回值  
        String result = null;  
  
        // 获取shell返回流  
        BufferedInputStream in = new BufferedInputStream(pcs.getInputStream());  
        // 字符流转换字节流  
        BufferedReader br = new BufferedReader(new InputStreamReader(in));  
        // 这里也可以输出文本日志  
  
        String lineStr;  
        try {
            while ((lineStr = br.readLine()) != null) {  
                result = lineStr;  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  
        // 关闭输入流  
        try {
            br.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
        logger.debug("linux file id = " + result);
        return result;
    }
    
    private static String runWindownCMD(String absoluteLogFilePath){
        Process pcs = null;
        try {
            pcs = rt.exec("cmd.exe /c dir \""+absoluteLogFilePath+"\" /tc");
            BufferedInputStream in = new BufferedInputStream(pcs.getInputStream());  
            // 字符流转换字节流  
            BufferedReader br = new BufferedReader(new InputStreamReader(in));  
            // 这里也可以输出文本日志  
            
            //window 运行输出格式为：
//            驱动器 C 中的卷是 Windows7_OS
//            卷的序列号是 F61B-CBD4
//
//            C:\Users\dengqb\Downloads 的目录
//
//            014/11/26  10:33               346 api-error.log
//                          1 个文件            346 字节
//                          0 个目录 82,801,864,704 可用字节
            //所以需要过滤前面5行
            for (int i=0; i<5; i++){
                br.readLine();
            }
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            String dateC = st.nextToken().replaceAll("/", "-");
            String time = st.nextToken().replaceAll(":", "-");
            
            String result = dateC+"-"+time;
            
         // 关闭输入流  
            try {
                br.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }  
            logger.debug("window file id = " + result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
