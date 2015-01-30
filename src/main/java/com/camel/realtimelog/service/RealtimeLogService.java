package com.camel.realtimelog.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.camel.realtimelog.action.ReadConfigAction;
import com.camel.realtimelog.action.ReadLogmarkFileAction;
import com.camel.realtimelog.action.group.LogAnalyseGroupAction;
import com.camel.realtimelog.domain.Configuration;
import com.camel.realtimelog.domain.Logmark;
import com.camel.realtimelog.domain.LogmarkFile;

public class RealtimeLogService {
    private Configuration config;
    private LogmarkFile logmarkFile;
    
    public void execute(){
        ReadConfigAction readConfigAction = new ReadConfigAction();
        ReadLogmarkFileAction readLogmarkFileAction =  new ReadLogmarkFileAction();
        config = new Configuration();
        //读取config配置文件
        readConfigAction.ReadConfig(config);

        logmarkFile = new LogmarkFile();
        readLogmarkFileAction.readLogmarkFile(config, logmarkFile);
        
        // 创建线程池，线程数量对应需要监控的log文件数量
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(config.getLogFileConfigs().size());
        
        for (Logmark logmark : logmarkFile.getLogmarks()) {
            //executor.submit(new LogAnalystJobAction(config));
            //executor.schedule(new LogAnalystJobAction(config), 10, TimeUnit.SECONDS);
            //executor.scheduleAtFixedRate(new LogAnalystJobAction(config), 5, 5, TimeUnit.SECONDS);
            //LogAnalystJobAction
            
            exec.scheduleAtFixedRate(new LogAnalyseGroupAction(logmark), 5, 10, TimeUnit.SECONDS);
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public LogmarkFile getLogmarkFile() {
        return logmarkFile;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

    public void setLogmarkFile(LogmarkFile logmarkFile) {
        this.logmarkFile = logmarkFile;
    }
}
