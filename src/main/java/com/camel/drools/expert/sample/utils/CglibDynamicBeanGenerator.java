/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

/**
 * 利用cglib的BeanGenerateBean动态生成Bean
 * @author dengqb
 * @date 2014年8月29日
 */
public class CglibDynamicBeanGenerator {
    private Object bean = null;  
    private BeanMap beanMap = null;  
      
    public CglibDynamicBeanGenerator(Map properties) {          
        this.bean = generateBean(properties);  
        this.beanMap = getBeanMap();  
    }  
      
    public void setValue(String property, Object value) {  
        beanMap.put(property, value);  
    }  
      
    public Object getValue(String property) {  
        return  beanMap.get(property);
    }  
  
    public Object getBean() {  
        return this.bean;  
    }    
     
    private Object generateBean(Map properties) {  
        BeanGenerator generator = new BeanGenerator();  
        Set keySet = properties.keySet();  
        for(Iterator i = keySet.iterator(); i.hasNext();) {  
            String key = (String)i.next();  
            generator.addProperty(key, (Class)properties.get(key));  
        }  
        return generator.create();  
    }  
      
    private BeanMap getBeanMap() {  
        return BeanMap.create(this.bean);  
    }  
}
