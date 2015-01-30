package com.camel.realtimelog.domain;

import com.camel.utils.FileUtils;

public class App {
    /**
     * 当前app应用的绝对路径
     */
    private static String AppAbsolutePath = "";

    /**
     * 获取当前运行app的绝对路径
     * @return
     */
    public static String getAppAbsolutePath() {
        if (AppAbsolutePath.isEmpty()) {
            setAppAbsolutePath(FileUtils.getFileAbsolutePath());
        } 
        return AppAbsolutePath;
    }

    public static void setAppAbsolutePath(String appAbsolutePath) {
        AppAbsolutePath = appAbsolutePath;
    }
    
    
}
