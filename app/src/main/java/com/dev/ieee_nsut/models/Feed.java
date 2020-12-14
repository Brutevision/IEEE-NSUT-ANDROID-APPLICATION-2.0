package com.dev.ieee_nsut.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */

public class Feed implements Parcelable{

    private float id;
    private String feedTitle;
    private String feedImageUrl;
    private String feedDetails;
    private String registerUrl;

    public Feed() {
    }

    protected Feed(Parcel in) {
        id = in.readFloat();
        feedTitle = in.readString();
        feedImageUrl = in.readString();
        feedDetails = in.readString();
        registerUrl = in.readString();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public com.dev.ieee_nsut.models.Feed createFromParcel(Parcel in) {
            return new com.dev.ieee_nsut.models.Feed(in);
        }

        @Override
        public com.dev.ieee_nsut.models.Feed[] newArray(int size) {
            return new com.dev.ieee_nsut.models.Feed[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(id);
        parcel.writeString(feedTitle);
        parcel.writeString(feedImageUrl);
        parcel.writeString(feedDetails);
        parcel.writeString(registerUrl);
    }

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getFeedImageUrl() {
        return feedImageUrl;
    }

    public void setFeedImageUrl(String feedImageUrl) {
        this.feedImageUrl = feedImageUrl;
    }

    public String getFeedTitle() {
        return feedTitle;
    }

    public void setFeedTitle(String feedTitle) {
        this.feedTitle = feedTitle;
    }

    public String getFeedDetails() {
        return feedDetails;
    }

    public void setFeedDetails(String feedDetails) {
        this.feedDetails = feedDetails;
    }

    public String getRegisterUrl() {
        return registerUrl;
    }

    public void setRegisterUrl(String registerUrl) {
        this.registerUrl = registerUrl;
    }
}
