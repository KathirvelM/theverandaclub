package com.verandah.club.data.model.category;

import com.verandah.club.data.model.home.ArticleData;

import java.util.List;

public class PreviousArticles {

    int current_page;

    List<ArticleData> data;

    public int getCurrent_page() {
        return current_page;
    }

    public List<ArticleData> getData() {
        return data;
    }
}
