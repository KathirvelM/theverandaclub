package com.verandah.club.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Section {

    @SerializedName("section_name")
    String name;

/*
    @SerializedName("sections")
    HashMap<String,Article> articleHashMap;
*/

    @SerializedName("datas")
    List<Article> articleHashMap;

    public String getName() {
        return name;
    }

    public List<Article> getArticleList(){
       // return new ArrayList<>(articleHashMap.values());
        return articleHashMap;
    }

}
