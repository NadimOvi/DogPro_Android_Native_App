package com.teleaus.bioscience.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BreedHistoryMainModel implements Parcelable {
    private ArrayList<BreedHistoryData> data;

    public BreedHistoryMainModel(ArrayList<BreedHistoryData> data) {
        this.data = data;
    }

    protected BreedHistoryMainModel(Parcel in) {
        data = in.createTypedArrayList(BreedHistoryData.CREATOR);
    }

    public static final Creator<BreedHistoryMainModel> CREATOR = new Creator<BreedHistoryMainModel>() {
        @Override
        public BreedHistoryMainModel createFromParcel(Parcel in) {
            return new BreedHistoryMainModel(in);
        }

        @Override
        public BreedHistoryMainModel[] newArray(int size) {
            return new BreedHistoryMainModel[size];
        }
    };

    public ArrayList<BreedHistoryData> getData() {
        return data;
    }

    public void setData(ArrayList<BreedHistoryData> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(data);
    }
}
