package com.verandah.club.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionUpdateResponse {
/*    @SerializedName("android")
    @Expose
    private Android android;

    public Android getAndroid() {
        return android;
    }*/
    @SerializedName("android")
    @Expose
    private VersionInfo android;

    public VersionInfo getAndroid() {
        return android;
    }

}