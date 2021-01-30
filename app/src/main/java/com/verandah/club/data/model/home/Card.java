package com.verandah.club.data.model.home;

import com.google.gson.annotations.SerializedName;

public class Card {

    @SerializedName(value = "thought", alternate = {"quote"})
    String content;

    @SerializedName(value = "author")
    String author;

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
