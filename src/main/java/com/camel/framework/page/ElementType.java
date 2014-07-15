package com.camel.framework.page;

public enum ElementType {

    FIRST("F"),
    MIDDLE("M"),
    LAST("L"),
    NONE("NA");

    private String type;

    private ElementType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
