package com.verandah.club.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {
    private String article_id;

  //  private sponsored_ad_id;

 //   private  sponsored_ad_data;


    @SerializedName(value = "article",alternate = {"article_data"})
    private ArticleData article_data;

    private ArticleData previous;

    private ArticleData next;

    @SerializedName(value = "sponsored_ad",alternate = {"sponsored_ad_data"})
    private ArticleData sponsored_ad_data;

    @SerializedName("related")
    private List<ArticleData> related;

    @SerializedName("data_type")
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    //   private null google_ads;


    public String getArticle_id() {
        return article_id;
    }

    public ArticleData getArticle_data() {
        return article_data!=null ?article_data :sponsored_ad_data;
    }

    public String getType() {
        return type;
    }

    public List<ArticleData> getRelated() {
        return related;
    }

    public ArticleData getPrevious() {
        return previous;
    }

    public ArticleData getNext() {
        return next;
    }
}
