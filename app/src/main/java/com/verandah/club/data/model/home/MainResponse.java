package com.verandah.club.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MainResponse {

    Card thoughts;
    Card quotes;

    @SerializedName("category")
    List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    @SerializedName("volume")
    List<Volume> volumeList;

    public List<Volume> getVolumeList() {
        return volumeList;
    }
/*
    @SerializedName("home_layouts")
    HashMap<String, HashMap<String,Section>> sectionListTemp;*/

    @SerializedName("home_layouts")
    List<Area> areaList;
    List<Section> sectionList;
    public List<Section> getSectionList(){
/*        if(sectionList!=null) return sectionList;

        List<HashMap<String,Section>> values = new ArrayList<>(sectionListTemp.values());
        sectionList = new ArrayList<>();
        for(HashMap<String,Section> value  : values){
            sectionList.addAll(new ArrayList<>(value.values()));
        }*/

        if(sectionList!=null) return sectionList;

        sectionList = new ArrayList<>();
        for(Area area  : areaList){
            sectionList.addAll(area.getSectionList());
        }

        return sectionList;
    }

    public Card getThoughts() {
        return thoughts;
    }

    public Card getQuotes() {
        return quotes;
    }
}
