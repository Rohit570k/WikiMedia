package com.example.wikimedia.data.models.Articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Continues {
    @SerializedName("gcmcontinue")
    @Expose
    private String gcmcontinue;

    @SerializedName("grncontinue")
    @Expose
    private String grncontinue;
    @SerializedName("continue")
    @Expose
    private String _continue;

    public String getGrncontinue() {
        return grncontinue;
    }

    public void setGrncontinue(String grncontinue) {
        this.grncontinue = grncontinue;
    }

    public String getGcmcontinue() {
        return gcmcontinue;
    }

    public void setGcmcontinue(String gcmcontinue) {
        this.gcmcontinue = gcmcontinue;
    }

    public String get_continue() {
        return _continue;
    }

    public void set_continue(String _continue) {
        this._continue = _continue;
    }
}
