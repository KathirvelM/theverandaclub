package com.verandah.club.data.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContactResponse {
    @SerializedName("email")
    @Expose
    ArrayList<String> email;
    /* *//*    @SerializedName("android")
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
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("phone")
    @Expose
    private ArrayList<String> phone;
    @SerializedName("address")
    @Expose
    private ArrayList<String> address;

    public App getApp() {
        return app;
    }

    @SerializedName("app")
    @Expose
    private App app;

    public Social getSocial() {
        return social;
    }

    @SerializedName("social")
    @Expose
    private Social social;

    public ContactResponse() {
    }

    public String getAbout() {
        return about;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public ArrayList<String> getphone() {
        return phone;
    }

    public ArrayList<String> getaddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return about;
    }

    public class App {
        @SerializedName("android")
        private String android;

        public String getAndroid() {
            return android;
        }

        public String getIos() {
            return ios;
        }

        @SerializedName("ios")
        @Expose
        private String ios;

    }

  public   class Social {
        @SerializedName("facebook")
        @Expose
        private String facebook;

      public String getFacebook() {
          return facebook;
      }

      public String getTwitter() {
          return twitter;
      }

      public String getYoutube() {
          return youtube;
      }

      public String getInstagram() {
          return instagram;
      }

      @SerializedName("twitter")
        @Expose
        private String twitter;

        @SerializedName("youtube")
        @Expose
        private String youtube;

        @SerializedName("instagram")
        @Expose
        private String instagram;

    }

}
