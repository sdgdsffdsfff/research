package mock.com.camel.drools.expert.sample.service;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.camel.drools.expert.sample.utils.CglibDynamicBeanGenerator;


public class DynamicBeanGeneratorMockTest {
    
    @Test
    public void generateBean () throws ClassNotFoundException{ 
        Map<String,Class<?>> properties = new HashMap<String,Class<?>>();
        properties.put("id", Class.forName("java.lang.Integer"));  
        properties.put("name", Class.forName("java.lang.String"));  
        properties.put("address", Class.forName("java.lang.String"));  
        // 生成动态 Bean  
        CglibDynamicBeanGenerator bean = new CglibDynamicBeanGenerator(properties);
        System.out.println("  OK!");  
        
        System.out.println("Set values ...");  
        // 给 Bean 设置值  
        bean.setValue("id", new Integer(123));  
        bean.setValue("name", "454");  
        bean.setValue("address", "789");  
        System.out.println("  OK!");  
          
        System.out.println("Get values");  
        // 从 Bean 中获取值，当然了获得值的类型是 Object  
        System.out.println("  >> id      = " + bean.getValue("id"));
        assertEquals(123, bean.getValue("id"));
        System.out.println("  >> name    = " + bean.getValue("name")); 
        assertEquals("454", bean.getValue("name"));
        System.out.println("  >> address = " + bean.getValue("address"));  
  
        System.out.println("Class name");  
        // 查看动态 Bean 的类名  
        Class clazz = bean.getBean().getClass();  
        System.out.println("  >> " + clazz.getName());  
          
        System.out.println("Show all methods");  
        // 查看动态 Bean 中声明的方法  
        Method[] methods = clazz.getDeclaredMethods();  
        for(int i = 0; i < methods.length; i++) {
            String methodName = methods[i].getName();
            System.out.println("  >> " + methodName);
            try {
                if (methodName.startsWith("get")){
                    System.out.println("   >>" + methods[i].invoke(bean.getBean()));
                }
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }  
  
        System.out.println("Show all properties");  
        // 查看动态 Bean 中声明的字段  
        Field[] fields = clazz.getDeclaredFields();  
        for(int i = 0; i < fields.length; i++) {  
            System.out.println("  >> " + fields[i].getName());  
        }   
    }
}
