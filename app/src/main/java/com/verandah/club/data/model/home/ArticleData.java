package com.verandah.club.data.model.home;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

import javax.inject.Singleton;

public class ArticleData {
    String url;

    private String image;

    private String id;

    private String[] categories;

    private String title;

    private String author;

    @SerializedName(value = "published_at", alternate = {"issue_date"})
    private String published_at;

    private String content;

    private String last_modified_at;

    private String[] tags;

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String[] getCategories ()
    {
        if(categories ==null || categories.length==0) {
            categories =new String[1];
            categories[0] = "Sponsored ads";
        }
        return categories;
    }

    public void setCategories (String[] categories)
    {
        this.categories = categories;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getPublished_at ()
    {
        return published_at;
    }

    public void setPublished_at (String published_at)
    {
        this.published_at = published_at;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getLast_modified_at ()
    {
        return last_modified_at;
    }

    public void setLast_modified_at (String last_modified_at)
    {
        this.last_modified_at = last_modified_at;
    }

    public String[] getTags ()
    {
        return tags;
    }

    public void setTags (String[] tags)
    {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }
}
