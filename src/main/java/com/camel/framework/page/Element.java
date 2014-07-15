package com.camel.framework.page;

public class Element {

    private int         key;

    private int         value;

    private Boolean     enable = true;

    private ElementType type   = ElementType.MIDDLE;

    public Element(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public Element(int key, int value, boolean enable) {
        this.key = key;
        this.value = value;
        this.enable = enable;
    }

    public Element(int key, int value, boolean enable, ElementType type) {
        this.enable = enable;
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    /* ------ static method ------------------------------------ */

    public static Element create(int i) {
        return new Element(i, i);
    }

    public static Element create(int i, boolean enable) {
        return new Element(i, i, enable);
    }

    public static Element create(int i, boolean enable, ElementType type) {
        return new Element(i, i, enable, type);
    }

    public static Element create(int i, int j, boolean enable, ElementType type) {
        return new Element(i, j, enable, type);
    }

    /* -------------------------------------------- */

    @Override
    public String toString() {
        return "Element [key=" + key + "\t value=" + value + "\t enable=" + enable + "\t type=" + type + "]";
    }

}
