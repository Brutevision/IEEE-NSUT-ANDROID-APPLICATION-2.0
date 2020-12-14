package com.dev.ieee_nsut.models;

public class Social {
    private String title;
    private String url;
    private int imageId;

    public Social(String title, int imageId, String url) {
        this.title = title;
        this.imageId = imageId;
        this.url = url;
    }
    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getUrl() {
        return url;
    }
}
