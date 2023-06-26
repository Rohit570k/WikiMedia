package com.example.wikimedia.data.models.Articles;

import java.util.Map;

public class Query {

    private Map<String,Pages> pages;

    public Map<String, Pages> getPages() {
        return pages;
    }

    public void setPages(Map<String, Pages> pages) {
        this.pages = pages;
    }
}
