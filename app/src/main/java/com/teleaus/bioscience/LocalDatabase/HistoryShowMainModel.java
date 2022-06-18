package com.teleaus.bioscience.LocalDatabase;

import java.util.ArrayList;

public class HistoryShowMainModel {
    ArrayList<HistoryShowData> data;

    public HistoryShowMainModel(ArrayList<HistoryShowData> data) {
        this.data = data;
    }

    public HistoryShowMainModel() {
    }

    public ArrayList<HistoryShowData> getData() {
        return data;
    }

    public void setData(ArrayList<HistoryShowData> data) {
        this.data = data;
    }
}
