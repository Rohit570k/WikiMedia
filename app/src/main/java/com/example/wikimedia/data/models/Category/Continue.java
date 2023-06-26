package com.example.wikimedia.data.models.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Continue {
    @SerializedName("accontinue")
    @Expose
    private String accontinue;
    @SerializedName("continue")
    @Expose
    private String _continue;

    /**
     * No args constructor for use in serialization
     *
     */
    public Continue() {
    }

    /**
     *
     * @param _continue
     * @param accontinue
     */
    public Continue(String accontinue, String _continue) {
        super();
        this.accontinue = accontinue;
        this._continue = _continue;
    }

    public String getAccontinue() {
        return accontinue;
    }

    public void setAccontinue(String accontinue) {
        this.accontinue = accontinue;
    }

    public String getContinue() {
        return _continue;
    }

    public void setContinue(String _continue) {
        this._continue = _continue;
    }
}
