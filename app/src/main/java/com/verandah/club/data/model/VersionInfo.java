package com.verandah.club.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionInfo {

    @SerializedName("app_version")
    @Expose
    private String version;
    @SerializedName("app_version_code")
    @Expose
    private int versionCode;
    @SerializedName("app_is_force_update")
    @Expose
    private boolean forceUpdate;
    @SerializedName("app_url")
    @Expose
    private String appUrl;

    public String getVersion() {
        return version;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public String getAppUrl() {
        return appUrl;
    }
}
