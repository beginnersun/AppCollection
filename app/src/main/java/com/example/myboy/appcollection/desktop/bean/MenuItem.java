package com.example.myboy.appcollection.desktop.bean;

public class MenuItem {

    private int id;
    private String title;
    private String uri;

    public MenuItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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
}
