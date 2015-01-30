package com.camel.realtimelog.domain;

public class MongdbConfig {
    /**
     * db ip
     */
    private String serverAddress;
    /**
     * db name
     */
    private String dbName;
    /**
     * collection name
     */
    private String collectionName;
    private String username;
    private String password;
    
    public String getServerAddress() {
        return serverAddress;
    }
    public String getDbName() {
        return dbName;
    }
    public String getCollectionName() {
        return collectionName;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
