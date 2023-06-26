package com.example.wikimedia.data.models.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allcategory {
    @SerializedName("category")
    @Expose
    private String category;

    /**
     * No args constructor for use in serialization
     *
     */
    public Allcategory() {
    }

    /**
     *
     * @param category
     */
    public Allcategory(String category) {
        super();
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
