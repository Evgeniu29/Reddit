package com.paad.reddit.model;


public class DataChild {
    private int dataChildId;
    private String title;
    private String author;
    private String date;
    private String thumbnail;
    private int num_comments;
    private long created_utc;
    private boolean viewed;

    public int getId() {
        return dataChildId;
    }

    public void setId(int dataChildId) {
        this.dataChildId = dataChildId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnail() {

        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {

        this.thumbnail = thumbnail;
    }

    public int getNum_comments() {

        return num_comments;
    }

    public void setNum_comments(int num_comments) {

        this.num_comments = num_comments;
    }

    public long getCreated_utc() {

        return created_utc;
    }

    public void setCreated_utc(long created_utc) {
        this.created_utc = created_utc;
    }
    
    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}
