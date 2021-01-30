package com.verandah.club.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    boolean isExpanded = false;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    int id;
    String name;
    @SerializedName("children")
    List<Category> categoryList;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }
}
