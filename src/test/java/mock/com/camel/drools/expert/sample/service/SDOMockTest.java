/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package mock.com.camel.drools.expert.sample.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.sdo.SDOProperty;
import org.junit.Before;
import org.junit.Test;

import commonj.sdo.DataObject;
import commonj.sdo.helper.DataFactory;
import commonj.sdo.helper.XSDHelper;

/**
 * DSO测试类
 * @author dengqb
 * @date 2014年9月1日
 */
public class SDOMockTest {
    
    private XSDHelper xsdHelper = XSDHelper.INSTANCE;
    private DataFactory dataFactory;
    
    @Before
    public void setup(){
      //先读取数据模型xsd文件
        InputStream in = SDOMockTest.class.getResourceAsStream("/inte/drools/xsd/RuleCondition.xsd");
        try {
            xsdHelper.define(in, null);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataFactory = DataFactory.INSTANCE;
    }
    
    /**
     * 创建SDO对象，并赋值
     */
    @Test
    public void createDSOTest_Success(){
        //创建SDO对象，namespace必须和xsd中的namespace一致
        DataObject userRule = dataFactory.create("http://drools.research.com/xsd/RuleCondition", "RuleCondition");
        List<String> destIncludes = new ArrayList<String>();
        destIncludes.add("china");
        destIncludes.add("brazil");
        destIncludes.add("US");
        
//        userRule.setDouble("amount", 50d);
//        userRule.setString("source", "ebay");
//        userRule.setList("destIncludes", destIncludes);
        Object obj = userRule.get("amount");
        assertTrue(obj instanceof Double);
        
        userRule.set("amount", 50.1d);
        userRule.set("source", "ebay");
        userRule.set("destIncludes", destIncludes);
        
        assertNotNull(userRule);
        
        assertEquals(50.1,userRule.getDouble("amount"),0);
        System.out.println(userRule.get("amount"));
        System.out.println(userRule.getString("source"));
        List<String> ls = userRule.getList("destIncludes");
        System.out.println(ls.get(0)+","+ls.get(1)+","+ls.get(2));
        
        System.out.println(userRule.getType());
        
    }
    
    @Test
    public void getSDOMethodsTest_Success(){
        //创建SDO对象，namespace必须和xsd中的namespace一致
        DataObject userRule = dataFactory.create("http://drools.research.com/xsd/RuleCondition", "RuleCondition");
        List<String> destIncludes = new ArrayList<String>();
        destIncludes.add("china");
        destIncludes.add("brazil");
        destIncludes.add("US");
        
        userRule.set("amount", "50.1d");
        userRule.set("source", "ebay");
        userRule.set("destIncludes", destIncludes);
        
        int size = userRule.getInstanceProperties().size();
        for (int i=0; i<size; i++){
            SDOProperty sdoProps = (SDOProperty) userRule.getInstanceProperties().get(i);
            String name = sdoProps.getName();
            Object value = userRule.get(name);
            if (value instanceof List){
                //List<String> valueLst = (List<String>) value;
                System.out.println(name+"="+((List)value).get(0)+","+((List)value).get(1)+","+((List)value).get(2));
            }else{
                System.out.println(name+"="+value.toString());
            }
        }
    }
}
