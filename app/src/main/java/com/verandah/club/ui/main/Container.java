package com.verandah.club.ui.main;

public class Container {
    int item;
    Integer type;
    Object object;

    public Container(int item,Integer type, Object object) {
        this.item = item;
        this.type = type;
        this.object = object;
    }

    public Integer getType() {
        return type;
    }

    public Integer getItem() {
        return item;
    }

    public Object getObject() {
        return object;
    }
}
