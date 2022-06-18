package com.teleaus.bioscience.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class DataClass implements Parcelable {
    private String breed;
    private String percentage;
    private String thumbnail;
    private boolean additional_info;
    private String country_of_origin;
    private String brief_history;
    private ArrayList<String> life_span;
    private ArrayList<String> ideal_weight;
    private ArrayList<String> ideal_height;
    private ArrayList<String> temperament;
    private String country_of_patronage;
    private ArrayList<String> utilization;
    private ArrayList<String> alternative_names;
    private String fci_classification;

    public DataClass() {
    }

    public DataClass(String breed, String percentage, String thumbnail, boolean additional_info, String country_of_origin, String brief_history, ArrayList<String> life_span, ArrayList<String> ideal_weight, ArrayList<String> ideal_height, ArrayList<String> temperament, String country_of_patronage, ArrayList<String> utilization, ArrayList<String> alternative_names, String fci_classification) {
        this.breed = breed;
        this.percentage = percentage;
        this.thumbnail = thumbnail;
        this.additional_info = additional_info;
        this.country_of_origin = country_of_origin;
        this.brief_history = brief_history;
        this.life_span = life_span;
        this.ideal_weight = ideal_weight;
        this.ideal_height = ideal_height;
        this.temperament = temperament;
        this.country_of_patronage = country_of_patronage;
        this.utilization = utilization;
        this.alternative_names = alternative_names;
        this.fci_classification = fci_classification;
    }

    protected DataClass(Parcel in) {
        breed = in.readString();
        percentage = in.readString();
        thumbnail = in.readString();
        additional_info = in.readByte() != 0;
        country_of_origin = in.readString();
        brief_history = in.readString();
        life_span = in.createStringArrayList();
        ideal_weight = in.createStringArrayList();
        ideal_height = in.createStringArrayList();
        temperament = in.createStringArrayList();
        country_of_patronage = in.readString();
        utilization = in.createStringArrayList();
        alternative_names = in.createStringArrayList();
        fci_classification = in.readString();
    }

    public static final Creator<DataClass> CREATOR = new Creator<DataClass>() {
        @Override
        public DataClass createFromParcel(Parcel in) {
            return new DataClass(in);
        }

        @Override
        public DataClass[] newArray(int size) {
            return new DataClass[size];
        }
    };

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(boolean additional_info) {
        this.additional_info = additional_info;
    }

    public String getCountry_of_origin() {
        return country_of_origin;
    }

    public void setCountry_of_origin(String country_of_origin) {
        this.country_of_origin = country_of_origin;
    }

    public String getBrief_history() {
        return brief_history;
    }

    public void setBrief_history(String brief_history) {
        this.brief_history = brief_history;
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

    public ArrayList<String> getTemperament() {
        return temperament;
    }

    public void setTemperament(ArrayList<String> temperament) {
        this.temperament = temperament;
    }

    public String getCountry_of_patronage() {
        return country_of_patronage;
    }

    public void setCountry_of_patronage(String country_of_patronage) {
        this.country_of_patronage = country_of_patronage;
    }

    public ArrayList<String> getUtilization() {
        return utilization;
    }

    public void setUtilization(ArrayList<String> utilization) {
        this.utilization = utilization;
    }

    public ArrayList<String> getAlternative_names() {
        return alternative_names;
    }

    public void setAlternative_names(ArrayList<String> alternative_names) {
        this.alternative_names = alternative_names;
    }

    public String getFci_classification() {
        return fci_classification;
    }

    public void setFci_classification(String fci_classification) {
        this.fci_classification = fci_classification;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(breed);
        parcel.writeString(percentage);
        parcel.writeString(thumbnail);
        parcel.writeByte((byte) (additional_info ? 1 : 0));
        parcel.writeString(country_of_origin);
        parcel.writeString(brief_history);
        parcel.writeStringList(life_span);
        parcel.writeStringList(ideal_weight);
        parcel.writeStringList(ideal_height);
        parcel.writeStringList(temperament);
        parcel.writeString(country_of_patronage);
        parcel.writeStringList(utilization);
        parcel.writeStringList(alternative_names);
        parcel.writeString(fci_classification);
    }
}
