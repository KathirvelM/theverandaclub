package com.verandah.club.data.model.category;

import com.google.gson.annotations.SerializedName;

import com.verandah.club.data.model.home.ArticleData;

import java.util.List;

public class CategoryResponse {

    Category category;

    @SerializedName("articles")
    List<ArticleData> articleList;

    public Category getCategory() {
        return category;
    }

    public List<ArticleData> getArticleList() {
        return articleList;
    }

    PreviousArticles previous_articles;

    public PreviousArticles getPrevious_articles() {
        return previous_articles;
    }
}