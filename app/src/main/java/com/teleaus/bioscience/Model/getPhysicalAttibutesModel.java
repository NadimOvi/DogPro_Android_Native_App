package com.teleaus.bioscience.Model;

import java.util.ArrayList;

public class getPhysicalAttibutesModel {
    private ArrayList<getPhysicalAttibutesDataModel> data;

    public getPhysicalAttibutesModel(ArrayList<getPhysicalAttibutesDataModel> data) {
        this.data = data;
    }

    public getPhysicalAttibutesModel() {
    }

    public ArrayList<getPhysicalAttibutesDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<getPhysicalAttibutesDataModel> data) {
        this.data = data;
    }
}
