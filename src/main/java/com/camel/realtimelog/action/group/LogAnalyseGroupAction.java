/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.realtimelog.action.group;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import com.camel.realtimelog.action.JobBreakValidateAction;
import com.camel.realtimelog.action.LogAnalystJobAction;
import com.camel.realtimelog.action.ReadLogFileAction;
import com.camel.realtimelog.domain.Logmark;
import com.mongodb.DBObject;

/**
 * 
 * @author dengqb
 * @date 2015年1月29日
 */
public class LogAnalyseGroupAction implements Runnable {
    private Logmark logmark;
    private JobBreakValidateAction jobBreakValidateAction;
    private LogAnalystJobAction logAnalystJobAction;
    private ReadLogFileAction readLogFileAction;
    
    public LogAnalyseGroupAction(final Logmark logmark){
        this.logmark = logmark;
        jobBreakValidateAction = new JobBreakValidateAction();
        readLogFileAction = new ReadLogFileAction();
        logAnalystJobAction = new LogAnalystJobAction();
    }
    
    @Override
    public void run() {
        boolean rvs = jobBreakValidateAction.validate(logmark);
        if (!rvs){
            InputStream is = readLogFileAction.readLogFile(logmark);
            if (is == null){
                return;
            }
            Map<String, DBObject> errorMap = logAnalystJobAction.analyse(is, logmark);
            Iterator it = errorMap.keySet().iterator();
            while(it.hasNext()){
                System.out.println(String.valueOf(it.next()));
            }
        }
    }
    
}
