package com.verandah.club.data.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.verandah.club.data.model.VersionInfo;

public class AboutResponse {
    /*    @SerializedName("android")
        @Expose
        private Android android;

        public Android getAndroid() {
            return android;
        }*/
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("content")
    @Expose
    private String content;

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

}
