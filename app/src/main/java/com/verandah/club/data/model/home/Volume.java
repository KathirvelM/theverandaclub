package com.verandah.club.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Volume {
    int id;
    String name;
    @SerializedName("issue")
    List<Issue> issueList;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }
}
