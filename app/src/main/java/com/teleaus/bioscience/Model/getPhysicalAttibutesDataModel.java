package com.teleaus.bioscience.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class getPhysicalAttibutesDataModel implements Parcelable {
    private String breed_id;
    private String breed_name;
    private String thumbnail;
    private ArrayList<String> life_span;
    private ArrayList<String> ideal_weight;
    private ArrayList<String> ideal_height;

    public getPhysicalAttibutesDataModel() {
    }

    public getPhysicalAttibutesDataModel(String breed_id, String breed, String thumbnail, ArrayList<String> life_span, ArrayList<String> ideal_weight, ArrayList<String> ideal_height) {
        this.breed_id = breed_id;
        this.breed_name = breed;
        this.thumbnail = thumbnail;
        this.life_span = life_span;
        this.ideal_weight = ideal_weight;
        this.ideal_height = ideal_height;
    }

    protected getPhysicalAttibutesDataModel(Parcel in) {
        breed_id = in.readString();
        breed_name = in.readString();
        thumbnail = in.readString();
        life_span = in.createStringArrayList();
        ideal_weight = in.createStringArrayList();
        ideal_height = in.createStringArrayList();
    }

    public static final Creator<getPhysicalAttibutesDataModel> CREATOR = new Creator<getPhysicalAttibutesDataModel>() {
        @Override
        public getPhysicalAttibutesDataModel createFromParcel(Parcel in) {
            return new getPhysicalAttibutesDataModel(in);
        }

        @Override
        public getPhysicalAttibutesDataModel[] newArray(int size) {
            return new getPhysicalAttibutesDataModel[size];
        }
    };

    public String getBreed_id() {
        return breed_id;
    }

    public void setBreed_id(String breed_id) {
        this.breed_id = breed_id;
    }

    public String getBreed() {
        return breed_name;
    }

    public void setBreed(String breed) {
        this.breed_name = breed;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ArrayList<String> getLife_span() {
        return life_span;
    }

    public void setLife_span(ArrayList<String> life_span) {
        this.life_span = life_span;
    }

    public ArrayList<String> getIdeal_weight() {
        return ideal_weight;
    }

    public void setIdeal_weight(ArrayList<String> ideal_weight) {
        this.ideal_weight = ideal_weight;
    }

    public ArrayList<String> getIdeal_height() {
        return ideal_height;
    }

    public void setIdeal_height(ArrayList<String> ideal_height) {
        this.ideal_height = ideal_height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(breed_id);
        parcel.writeString(breed_name);
        parcel.writeString(thumbnail);
        parcel.writeStringList(life_span);
        parcel.writeStringList(ideal_weight);
        parcel.writeStringList(ideal_height);
    }
}
