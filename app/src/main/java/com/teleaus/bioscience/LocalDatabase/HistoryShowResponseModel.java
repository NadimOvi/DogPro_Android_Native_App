package com.teleaus.bioscience.LocalDatabase;

import com.teleaus.bioscience.Model.BreedHistoryResponseData;

import java.util.ArrayList;

public class HistoryShowResponseModel {
    ArrayList<LocalHistoryResponseData> data;
    private String classification_summary;

    public HistoryShowResponseModel() {
    }

    public HistoryShowResponseModel(ArrayList<LocalHistoryResponseData> data, String classification_summary) {
        this.data = data;
        this.classification_summary = classification_summary;
    }

    public ArrayList<LocalHistoryResponseData> getData() {
        return data;
    }

    public void setData(ArrayList<LocalHistoryResponseData> data) {
        this.data = data;
    }

    public String getClassification_summary() {
        return classification_summary;
    }

    public void setClassification_summary(String classification_summary) {
        this.classification_summary = classification_summary;
    }
}
