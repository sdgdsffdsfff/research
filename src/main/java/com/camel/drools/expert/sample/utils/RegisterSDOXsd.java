package com.camel.drools.expert.sample.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import commonj.sdo.helper.XSDHelper;

/**
 * 加载SDO xsd定义文件
 * 
 * @author sunkey
 * @date Sep 3, 2013
 */
public class RegisterSDOXsd {

    private static List<String> xsdList = new ArrayList<String>();

    protected final Log logger = LogFactory.getLog(getClass());
    
    private String xsdFilePath;

    /**
     * 注册SDO xsd
     */
    public void register() {
        loadXsdList();
        for (String xsdPath : xsdList) {
            logger.info("loading " + xsdPath);
            InputStream in = getClass().getResourceAsStream(xsdPath);
            XSDHelper.INSTANCE.define(in, null);
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }
    
    /**
     * 重新加载SDO xsd文件
     */
    public void reloadXsd(){
        register();
    }
    
    /**
     * 加载xsd list
     */
    private void loadXsdList(){
        String classPath = null;
        try {
            classPath = RegisterSDOXsd.class.getResource("/").toURI().getPath();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        File file = new File(classPath+xsdFilePath);
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                System.out.println("line " + line +": "+ tempString);
                if (!tempString.trim().startsWith("#")){
                    xsdList.add(tempString);
                }
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try{
                    reader.close();
                } catch(IOException e){
                }
            }
        }
        
    }
    
    public void setXsdList(List<String> xsdList) {
        this.xsdList = xsdList;
    }

    public String getXsdFilePath() {
        return xsdFilePath;
    }

    public void setXsdFilePath(String xsdFilePath) {
        this.xsdFilePath = xsdFilePath;
    }

}
