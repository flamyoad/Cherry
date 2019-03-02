package com.flamyoad.android.cherry.db.entity;

public class Chapter {

    private String title;
    private String chapterNumber;
    private String date;
    private boolean isRead;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
