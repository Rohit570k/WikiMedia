package com.example.wikimedia.data.models.Articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Revisions {
    private String contentformat;
    private String contentmodel;
    @Expose
    @SerializedName("*")
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
