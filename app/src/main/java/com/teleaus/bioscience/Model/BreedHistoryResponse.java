package com.teleaus.bioscience.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BreedHistoryResponse implements Parcelable {
    private ArrayList<BreedHistoryResponseData> data;
    private String classification_summary;

    public BreedHistoryResponse() {
    }

    public BreedHistoryResponse(ArrayList<BreedHistoryResponseData> data, String classification_summary) {
        this.data = data;
        this.classification_summary = classification_summary;
    }

    protected BreedHistoryResponse(Parcel in) {
        classification_summary = in.readString();
    }

    public static final Creator<BreedHistoryResponse> CREATOR = new Creator<BreedHistoryResponse>() {
        @Override
        public BreedHistoryResponse createFromParcel(Parcel in) {
            return new BreedHistoryResponse(in);
        }

        @Override
        public BreedHistoryResponse[] newArray(int size) {
            return new BreedHistoryResponse[size];
        }
    };

    public ArrayList<BreedHistoryResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<BreedHistoryResponseData> data) {
        this.data = data;
    }

    public String getClassification_summary() {
        return classification_summary;
    }

    public void setClassification_summary(String classification_summary) {
        this.classification_summary = classification_summary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(classification_summary);
    }
}
