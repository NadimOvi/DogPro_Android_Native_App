package com.teleaus.bioscience.Model;

import java.util.ArrayList;

public class BreedMainModelList {
    ArrayList<DataList> data;

    public BreedMainModelList() {
    }

    public BreedMainModelList(ArrayList<DataList> data) {
        this.data = data;
    }

    public ArrayList<DataList> getData() {
        return data;
    }

    public void setData(ArrayList<DataList> data) {
        this.data = data;
    }
}
