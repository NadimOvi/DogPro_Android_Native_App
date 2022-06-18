package com.teleaus.bioscience.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class MainResponseModelClass implements Serializable {

    private String classification_summary;
    ArrayList<DataClass> data;

    public MainResponseModelClass() {
    }

    public MainResponseModelClass(String classification_summary, ArrayList<DataClass> data) {
        this.classification_summary = classification_summary;
        this.data = data;
    }

    public String getClassification_summary() {
        return classification_summary;
    }

    public void setClassification_summary(String classification_summary) {
        this.classification_summary = classification_summary;
    }

    public ArrayList<DataClass> getData() {
        return data;
    }

    public void setData(ArrayList<DataClass> data) {
        this.data = data;
    }
}
