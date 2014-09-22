/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.drools.core.impl.InternalKnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;

import com.camel.drools.expert.sample.utils.RegisterSDOXsd;

/**
 * 由于通过spring配置文件创建KBase错误，
 * 现在通过单列方法创建KBase
 * @author dengqb
 * @date 2014年8月29日
 */
public class KBaseContext {
    
    /**
     * kBase
     */
    private static InternalKnowledgeBase kBase;
    
    /**
     * 页面规则表达式与SDO属性名称的映射
     */
    private static Properties exprToSDOProps;
    
    /**
     * 当前的类路径
     */
    private static String classPath;
    
    /**
     * 获取单例kbase
     * @return
     */
    public static InternalKnowledgeBase getKBaseInstance(){
        if (kBase == null){
            kBase = KBaseContainer.instance;
        }
        System.setProperty("drools.dateformat","yyyy-MM-dd HH:mm:ss");
        return kBase;
    }
    
    private static class KBaseContainer {
        private static InternalKnowledgeBase instance = (InternalKnowledgeBase) KnowledgeBaseFactory.newKnowledgeBase();
    }
    
    /**
     * 获取dsl key 和user rule key的映射关系
     * @return
     */
    public static Properties getExprToSDOMapping(){
        if (exprToSDOProps == null){
            InputStream in = KBaseContext.class.getResourceAsStream("/drools/exprToSdoMapping.properties");
            try {
                exprToSDOProps = new Properties();
                exprToSDOProps.load(in);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if (in != null){
                    try {
                        in.close();
                    }catch(IOException e){
                        
                    }
                }
            }
        }
        return exprToSDOProps;
    }
    
    /**
     * 获取当期系统的class类路径
     * @return
     */
    public static String getClassPath(){
        classPath = KBaseContext.class.getResource("/").getPath().toString();
        return classPath;
    }
    
}
