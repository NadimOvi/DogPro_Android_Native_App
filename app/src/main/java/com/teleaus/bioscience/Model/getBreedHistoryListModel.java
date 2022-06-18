package com.teleaus.bioscience.Model;

import java.util.ArrayList;

public class getBreedHistoryListModel {
    private ArrayList<getBreedHistoryListModelData> data;

    public getBreedHistoryListModel(ArrayList<getBreedHistoryListModelData> data) {
        this.data = data;
    }

    public getBreedHistoryListModel() {
    }

    public ArrayList<getBreedHistoryListModelData> getData() {
        return data;
    }

    public void setData(ArrayList<getBreedHistoryListModelData> data) {
        this.data = data;
    }
}
