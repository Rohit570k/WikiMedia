package com.example.wikimedia.data.models.Articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ArticleResponse {
    @Expose
    @SerializedName("batchcomplete")
    private Boolean batchcomplete;

//    @Expose
//    @SerializedName("continue")
//    private Continues total;

    @Expose
    @SerializedName("query")
    private Query query;


    public Boolean getBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(Boolean batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
