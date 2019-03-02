package com.flamyoad.android.cherry.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookSearchResults")
public class BookSearch {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String url;
    private String latestChap;
    private String thumbnail;

    public BookSearch() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

