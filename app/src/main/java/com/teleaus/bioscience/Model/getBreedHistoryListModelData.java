package com.teleaus.bioscience.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class getBreedHistoryListModelData implements Parcelable {
    private String breed_id;
    private String breed_name;
    private String history;
    private String thumbnail;

    public getBreedHistoryListModelData(String breed_id, String breed_name, String history, String thumbnail) {
        this.breed_id = breed_id;
        this.breed_name = breed_name;
        this.history = history;
        this.thumbnail = thumbnail;
    }

    public getBreedHistoryListModelData() {
    }

    protected getBreedHistoryListModelData(Parcel in) {
        breed_id = in.readString();
        breed_name = in.readString();
        history = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<getBreedHistoryListModelData> CREATOR = new Creator<getBreedHistoryListModelData>() {
        @Override
        public getBreedHistoryListModelData createFromParcel(Parcel in) {
            return new getBreedHistoryListModelData(in);
        }

        @Override
        public getBreedHistoryListModelData[] newArray(int size) {
            return new getBreedHistoryListModelData[size];
        }
    };

    public String getBreed_id() {
        return breed_id;
    }

    public void setBreed_id(String breed_id) {
        this.breed_id = breed_id;
    }

    public String getBreed_name() {
        return breed_name;
    }

    public void setBreed_name(String breed_name) {
        this.breed_name = breed_name;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
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
        parcel.writeString(breed_id);
        parcel.writeString(breed_name);
        parcel.writeString(history);
        parcel.writeString(thumbnail);
    }
}
