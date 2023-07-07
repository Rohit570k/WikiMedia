package com.example.wikimedia.data.models.Articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ArticleResponse {
    @Expose
    @SerializedName("batchcomplete")
    private Boolean batchcomplete;

    @Expose
    @SerializedName("continue")
    private Continues _continue;

    @Expose
    @SerializedName("query")
    private Query query;

    public Continues get_continue() {
        return _continue;
    }

    public void set_continue(Continues _continue) {
        this._continue = _continue;
    }

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
