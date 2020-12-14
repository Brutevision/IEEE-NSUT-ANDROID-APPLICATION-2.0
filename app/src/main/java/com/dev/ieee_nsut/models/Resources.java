package com.dev.ieee_nsut.models;

/**
 * 
 */

public class Resources {

    private String mTitle;

    private int mImageResId;

    private String mUrl;

    private int bgColorId;

    public Resources(String mTitle, int mImageResId, String mUrl, int bgColorId) {
        this.mTitle = mTitle;
        this.mImageResId = mImageResId;
        this.mUrl = mUrl;
        this.bgColorId = bgColorId;
    }

    public int getBgColorId() {
        return bgColorId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmImageResId() {
        return mImageResId;
    }

    public void setmImageResId(int mImageResId) {
        this.mImageResId = mImageResId;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
