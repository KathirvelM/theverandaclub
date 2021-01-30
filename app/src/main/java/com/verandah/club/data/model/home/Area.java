package com.verandah.club.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Area {
    int area_id;
    @SerializedName("sections")
    List<Section> sectionList;

    public int getArea_id() {
        return area_id;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }
}
