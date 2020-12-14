package com.dev.ieee_nsut.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 
 */

public class Information implements Parcelable{

    private float id;
    private String title;
    private String description;
    private ArrayList<String> imageList;
    private String date;

    public Information() {
    }

    protected Information(Parcel in) {
        id = in.readFloat();
        title = in.readString();
        description = in.readString();
        imageList = in.createStringArrayList();
        date = in.readString();
    }

    public static final Creator<Information> CREATOR = new Creator<Information>() {
        @Override
        public com.dev.ieee_nsut.models.Information createFromParcel(Parcel in) {
            return new com.dev.ieee_nsut.models.Information(in);
        }

        @Override
        public com.dev.ieee_nsut.models.Information[] newArray(int size) {
            return new com.dev.ieee_nsut.models.Information[size];
        }
    };

    public float getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getImageList() {
        return imageList;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeStringList(imageList);
        parcel.writeString(date);
    }
}
