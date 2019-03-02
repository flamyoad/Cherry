package com.flamyoad.android.cherry.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
//    @PrimaryKey(autoGenerate = true)
//    private int id;

    @PrimaryKey @NonNull
    private String uniqueName;

    private String title;
    private String url;
    private String latestChap;
    private String thumbnail;

    public Book() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatestChap() {
        return latestChap;
    }

    public void setLatestChap(String latestChap) {
        this.latestChap = latestChap;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

}
