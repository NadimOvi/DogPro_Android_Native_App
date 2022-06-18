package com.teleaus.bioscience.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class DataList implements Parcelable {
    private String name;
    private String thumbnail;

    public DataList() {
    }

    public DataList(String name, String thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    protected DataList(Parcel in) {
        name = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<DataList> CREATOR = new Creator<DataList>() {
        @Override
        public DataList createFromParcel(Parcel in) {
            return new DataList(in);
        }

        @Override
        public DataList[] newArray(int size) {
            return new DataList[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(thumbnail);
    }
}
