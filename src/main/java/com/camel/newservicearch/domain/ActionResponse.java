package com.camel.newservicearch.domain;

import java.util.ArrayList;
import java.util.List;

public class ActionResponse {
    
    private boolean hasError = false;
    private List<ErrorInfo> errors;
    private String message;
    
    public boolean isHasError() {
        if (errors != null && errors.size() > 0){
            hasError = true;
        }else{
            hasError = false;
        }
        return hasError;
    }
    public String getMessage() {
        return message;
    }
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<ErrorInfo> getErrors() {
        if (errors == null){
            errors = new ArrayList<ErrorInfo>();
        }
        return errors;
    }
    
    public void setErrors(List<ErrorInfo> errors) {
        this.errors = errors;
    }
    
    public void addError(ErrorInfo error){
        getErrors().add(error);
    }
}
