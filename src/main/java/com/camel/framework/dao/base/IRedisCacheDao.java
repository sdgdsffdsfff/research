/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.framework.dao.base;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * REDIS缓存DAO管理接口
 * 
 * @author Shuah
 * @date 2014年3月19日
 */
public interface IRedisCacheDao {
    /**
     * 存储对象
     * @param key 存放缓存的KEY
     * @param value 需存储的对象
     */
    void set(String key, Object value);
    
    /**
     * 获取缓存中的对象
     * @param key 存放缓存的KEY
     * @return String
     */
    String get(String key);
    
    /**
     * 判断缓存中是否存在指定KEY
     * @param key 存放缓存的KEY
     * @return boolean
     */
    boolean exists(String key);
    
    /**
     * 获取缓存中的对象
     * @param <T> PO对象
     * @param key 存放缓存的KEY
     * @param clazz 需转换的对象类型
     * @return <T> T
     */
    <T> T get(String key, Class<T> clazz);
    
    /**
     * 获取缓存中的对象集合
     * @param <T> PO对象
     * @param key 存放缓存的KEY
     * @param collectionClass 集合类型 如List、HashMap等(详见constructCollectionType方法)
     * @param elementClasses 集合中具体元素类型(详见constructCollectionType方法)
     * @return <T> T
     */
    <T> T get(String key, Class<?> collectionClass, Class<?> ... elementClasses);
    
    /**
     * 用Hash的方式存储对象
     * @param key 存放缓存的KEY
     * @param field 对应的字段名
     * @param value 需存储的对象
     */
    void hset(String key, String field, Object value);
    
    /**
     * 删除KEY对应的缓存信息
     * @param key 存放缓存的KEY
     */
    void del(String key);
    
    /**
     * 用Hash的方式获取缓存中的对象
     * @param key 存放缓存的KEY
     * @param field 对应的字段名
     * @return String
     */
    String hget(String key, String field);
    
    /**
     * 获取缓存中该KEY对应的Set集合
     * @param key KEY值
     * @return Set<String>
     */
    Set<String> hkeys(String key);
    
    /**
     * 得到这个KEY对应的Map集合
     * @param key KEY值
     * @return Map<String, String>
     */
    Map<String, String> hgetAll(String key);
    
    /**
     * 获取缓存中该KEY对应的值对象集合
     * @param <T> PO对象
     * @param key KEY值
     * @param clazz 对象类型
     * @return <T> List<T>
     */
    <T> List<T> hgetAll(String key, Class<T> clazz);
    
    /**
     * 用Hash的方式获取缓存中的对象
     * @param <T> PO对象
     * @param key 存放缓存的KEY
     * @param field 对应的字段名
     * @param clazz 需转换的对象类型
     * @return <T> T
     */
    <T> T hget(String key, String field, Class<T> clazz);
    
    
    /**
     * 获取缓存中的对象集合
     * @param <T> PO对象
     * @param key 存放缓存的KEY
     * @param field 字段名
     * @param collectionClass 集合类型 如List、HashMap等(详见constructCollectionType方法)
     * @param elementClasses 集合中具体元素类型(详见constructCollectionType方法)
     * @return <T> T
     */
    <T> T hget(String key, String field, Class<?> collectionClass, Class<?> ... elementClasses);
}
