package com.example.ganeshshetty.task_chumbak;

/**
 * Created by Ganesh Shetty on 09-03-2017.
 */

public class Movie {
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    int page;
    long id;
    String title, thumbnail;

    public Movie(long id, String title, String thumbnail,int page) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
