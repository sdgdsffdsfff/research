package com.camel.newservicearch.domain;

public class ErrorInfo {
    private String errorMessage;
    private String error;
    
    public String getErrorMessage() {
        return errorMessage;
    }
    public String getError() {
        return error;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void setError(String error) {
        this.error = error;
    }
}
