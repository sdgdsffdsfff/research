/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Properties;

import javax.sql.rowset.spi.SyncResolver;

/**
 * 文件工具
 * @author dengqb
 * @date 2014年9月2日
 */
public class FileUtils {
    
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLine(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            StringBuilder sb = new StringBuilder();
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }
    
    /**
     * 写入文件
     * @param file
     * @param content
     */
    public static void writeFile(File file, String content){
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{
                if (fw != null){
                    fw.close();
                }
            }catch (IOException e){
                
            }
        }
    }
    
    /**
     * 读取property属性文件
     * @param filePath
     * @return
     */
    public static Properties readProperties(String filePath){
        Properties props = new Properties();
        InputStream in;
        try {
            in = new FileInputStream(filePath);
            props.load(in);
            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return props;
    }
    
    /**
     * 更新（或插入）一对properties信息(主键及其键值)
     * 如果该主键已经存在，更新该主键的值；
     * 如果该主键不存在，则插件一对键值。
     * @param filePath
     * @param propKey
     * @param propValue
     */
    public static void writeProperties(String filePath, String propKey, String propValue ){
        Properties prop = new Properties();
        prop.setProperty(propKey, propValue);
        try {
            OutputStream outputStream = new FileOutputStream(filePath);
            prop.store(outputStream, "save or update: "+ propKey + "=" + propValue);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 更新（或插入）多条properties信息(主键及其键值)
     * 如果该主键已经存在，更新该主键的值；
     * 如果该主键不存在，则插件一对键值。
     * @param filePath
     * @param propKey
     * @param propValue
     */
    public synchronized static void writeProperties(String filePath, Properties props ){
        Properties oldProp = readProperties(filePath);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath);
//            System.out.println("ready update mark info");
            Iterator it = props.keySet().iterator();
            while(it.hasNext()){
                String key = (String) it.next();
                oldProp.setProperty(key, props.getProperty(key));
            }
            oldProp.store(outputStream, "save or update "+ props.size() +" property");
//            System.out.println("mark info updated");
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 文件是否存在
     * @param filePath
     * @return
     */
    public static boolean isExist(String filePath){
        File file = new File(filePath);
        return file.exists();
    }
    
    /**
     * 获取应用的绝对路径
     * @return
     */
    public static String getFileAbsolutePath(){
        URL url = FileUtils.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 可执行jar包运行的结果里包含".jar" 
        if (filePath.endsWith(".jar")){
            // 截取路径中的jar包名  
            filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);  
        }
        
        File file = new File(filePath);
        filePath = file.getAbsolutePath();//得到windows下的正确路径  
        return filePath;
    }
    
    /**
     * 创建文件，并返回文件绝对路径
     * @param filePath
     * @return
     */
    public synchronized static String createFile(String filePath){
        File file = new File(filePath);
        boolean result = false;
        if (!file.exists()){
            try {
                result = file.createNewFile();
                if (result){
                    return file.getAbsolutePath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return file.getAbsolutePath();
        }
        return null;
    }
}
