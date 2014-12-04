/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog;

import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

/**
 * 
 * @author dengqb
 * @date 2014年11月6日
 */
public interface IPersistenceAdapter {
    
    void saveAll(List<DBObject> logObjects) throws Exception;
    
    void saveOne(String logStr);
    
    void findAll();
    
    void getOne();
    
    void deleteOne();
}
