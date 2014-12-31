/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/**
 * 
 * @author dengqb
 * @date 2014年11月6日
 */
public class PersistenceMongoAccessor implements IPersistenceAdapter {
    private static final Logger logger = Logger.getLogger(PersistenceMongoAccessor.class);
    
    private String serverAddress;
    private String dbName;
    private String collectionName;
    private String username;
    private String password;
    private static MongoClient mongo;
    private static DB db;
    private static DBCollection logs;
    
    public PersistenceMongoAccessor(String serverAddress, String dbName, String collectionName, String username, String password){
        this.serverAddress = serverAddress;
        this.dbName = dbName;
        this.collectionName = collectionName;
        this.username = username;
        this.password = password;
        setMongoClient();
    }
    
    private void setMongoClient (){
        if (mongo == null){
            try {
                ServerAddress sa = new ServerAddress(serverAddress);
                MongoCredential mc = MongoCredential.createMongoCRCredential(username, dbName,password.toCharArray());
                List<MongoCredential> mcs = new ArrayList<MongoCredential>();
                mcs.add(mc);
                mongo = new MongoClient(sa, mcs);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            db = mongo.getDB(dbName);
            logs = db.getCollection(collectionName);
        }
    }
    
    public void destory(){
        if (mongo != null){
            mongo.close();
        }
        mongo = null;
        db = null;
        logs = null;
    }
    
    @Override
    public void saveAll(List<DBObject> logObjects) throws Exception {
        BulkWriteOperation operation = logs.initializeUnorderedBulkOperation();
        for (DBObject dbObj: logObjects){
            operation.insert(dbObj);
        }
        operation.execute();
        //logs.insert(logObjects);
        logger.debug("save "+logObjects.size()+" errror logs success");
    }

    @Override
    public void saveOne(String logStr) {
        // TODO Auto-generated method stub
    }

    @Override
    public void findAll() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void getOne() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteOne() {
        // TODO Auto-generated method stub
        
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public DBCollection getLogs() {
        return logs;
    }

    public void setLogs(DBCollection logs) {
        this.logs = logs;
    }
}
