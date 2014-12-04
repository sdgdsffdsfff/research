/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.email;

/**
 * 
 * @author dengqb
 * @date 2014年11月14日
 */
public class SimpleMail {
    private String subject;
    private String content;
    
    public String getSubject() {
        return subject;
    }
    public String getContent() {
        return content;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
