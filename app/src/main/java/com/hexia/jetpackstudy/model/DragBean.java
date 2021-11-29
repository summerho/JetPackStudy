package com.hexia.jetpackstudy.model;

public class DragBean {

    public String name;

    public String code;

    public int type; // 1为已选，0为未选

    public DragBean() {}

    public DragBean(String name, String code, int type) {
        this.name = name;
        this.code = code;
        this.type = type;
    }

    @Override
    public String toString() {
        return "DragBean{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
