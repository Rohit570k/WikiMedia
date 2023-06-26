package com.example.wikimedia.data.models.Articles;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
@Entity(tableName = "pages")
public class Pages {
    @PrimaryKey
    @ColumnInfo(name = "pageid")
    private int pageid;
    @Ignore
    private int ns;
    @ColumnInfo(name = "title")
    private String title;
    @Ignore
    private List<Revisions> revisions;
    @Ignore
    private List<Images> images;
    @Embedded
    private Thumbnail thumbnail;
    @ColumnInfo(name = "pageimage")
    private String  pageimage;
    @Expose
    @SerializedName("extract")
    @ColumnInfo(name = "extracts")
    private String extracts;


    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPageimage() {
        return pageimage;
    }

    public void setPageimage(String pageimage) {
        this.pageimage = pageimage;
    }

    public int getPageid() {
        return pageid;
    }

    public void setPageid(int pageid) {
        this.pageid = pageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Revisions> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revisions> revisions) {
        this.revisions = revisions;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public String getExtracts() {
        return extracts;
    }

    public void setExtracts(String extracts) {
        this.extracts = extracts;
    }
}
