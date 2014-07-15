package com.camel.research.pmd;

public class ConstructorCallsOverridableMethod {

    public ConstructorCallsOverridableMethod(){
        getString(); //may throw NullPointerException if overridden
    }
    public String toString(){
      return "IAmSeniorClass";
    }
    private String getString(){
        return toString();
    }
}
