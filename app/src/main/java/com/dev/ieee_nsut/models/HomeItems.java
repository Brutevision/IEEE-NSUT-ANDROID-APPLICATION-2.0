package com.dev.ieee_nsut.models;

/**
 * 
 */

public class HomeItems {

    private String title;
    private int drawableId;
    private int bgDrawableId;

    public HomeItems(String title, int drawableId, int bgDrawableId) {
        this.title = title;
        this.drawableId = drawableId;
        this.bgDrawableId = bgDrawableId;
    }

    public String getTitle() {
        return title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getBgDrawableId() {
        return bgDrawableId;
    }
}
