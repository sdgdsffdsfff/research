/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
package com.camel.drools.expert;

/**
 * 规则引擎example，驾照申请人
 * @author dengqb
 * @date 2014年3月20日
 */
public class Applicant {
    private String name;
    private int age;
    private String sex;
    
    public Applicant(String name, int age){
        this.name = name;
        this.age = age;
    }
    
    public Applicant(String name, int age, String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
