/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert.sample.utils;

import java.io.IOException;
import java.io.Writer;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 处理freemarker template excetion
 * @author dengqb
 * @date 2014年9月5日
 */
public class FMTemplateExceptionHandler implements TemplateExceptionHandler {

    @Override
    public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
        try {
            out.write("[ERROR:"+ te.getMessage()+ "]");
        } catch (IOException e) {
            throw new TemplateException("Failed to print error message. Cause: " + e, env);
        }
        throw new RuntimeException("Freemarker template process error:"+te);
    }

}
