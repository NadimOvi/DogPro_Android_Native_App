package com.teleaus.bioscience.LocalDatabase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LocalHistoryResponseData implements Parcelable {
    private String breed;
    private String breed_id;
    private ArrayList<String> life_span;

    private String thumbnail;
    private String percentage;
    private ArrayList<String> temperament;
    private ArrayList<String> utilization;
    private ArrayList<String> ideal_height;
    private ArrayList<String> ideal_weight;
    private String brief_history;
    private boolean additional_info;
    private ArrayList<String> alternative_names;
    private String country_of_origin;
    private String fci_classification;
    private String country_of_patronage;

    public LocalHistoryResponseData() {
    }

    public LocalHistoryResponseData(String breed, String breed_id, ArrayList<String> life_span, String thumbnail, String percentage, ArrayList<String> temperament, ArrayList<String> utilization, ArrayList<String> ideal_height, ArrayList<String> ideal_weight, String brief_history, boolean additional_info, ArrayList<String> alternative_names, String country_of_origin, String fci_classification, String country_of_patronage) {
        this.breed = breed;
        this.breed_id = breed_id;
        this.life_span = life_span;
        this.thumbnail = thumbnail;
        this.percentage = percentage;
        this.temperament = temperament;
        this.utilization = utilization;
        this.ideal_height = ideal_height;
        this.ideal_weight = ideal_weight;
        this.brief_history = brief_history;
        this.additional_info = additional_info;
        this.alternative_names = alternative_names;
        this.country_of_origin = country_of_origin;
        this.fci_classification = fci_classification;
        this.country_of_patronage = country_of_patronage;
    }

    protected LocalHistoryResponseData(Parcel in) {
        breed = in.readString();
        breed_id = in.readString();
        life_span = in.createStringArrayList();
        thumbnail = in.readString();
        percentage = in.readString();
        temperament = in.createStringArrayList();
        utilization = in.createStringArrayList();
        ideal_height = in.createStringArrayList();
        ideal_weight = in.createStringArrayList();
        brief_history = in.readString();
        additional_info = in.readByte() != 0;
        alternative_names = in.createStringArrayList();
        country_of_origin = in.readString();
        fci_classification = in.readString();
        country_of_patronage = in.readString();
    }

    public static final Creator<LocalHistoryResponseData> CREATOR = new Creator<LocalHistoryResponseData>() {
        @Override
        public LocalHistoryResponseData createFromParcel(Parcel in) {
            return new LocalHistoryResponseData(in);
        }

        @Override
        public LocalHistoryResponseData[] newArray(int size) {
            return new LocalHistoryResponseData[size];
        }
    };

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getBreed_id() {
        return breed_id;
    }

    public void setBreed_id(String breed_id) {
        this.breed_id = breed_id;
    }

    public ArrayList<String> getLife_span() {
        return life_span;
    }

    public void setLife_span(ArrayList<String> life_span) {
        this.life_span = life_span;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public ArrayList<String> getTemperament() {
        return temperament;
    }

    public void setTemperament(ArrayList<String> temperament) {
        this.temperament = temperament;
    }

    public ArrayList<String> getUtilization() {
        return utilization;
    }

    public void setUtilization(ArrayList<String> utilization) {
        this.utilization = utilization;
    }

    public ArrayList<String> getIdeal_height() {
        return ideal_height;
    }

    public void setIdeal_height(ArrayList<String> ideal_height) {
        this.ideal_height = ideal_height;
    }

    public ArrayList<String> getIdeal_weight() {
        return ideal_weight;
    }

    public void setIdeal_weight(ArrayList<String> ideal_weight) {
        this.ideal_weight = ideal_weight;
    }

    public String getBrief_history() {
        return brief_history;
    }

    public void setBrief_history(String brief_history) {
        this.brief_history = brief_history;
    }

    public boolean isAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(boolean additional_info) {
        this.additional_info = additional_info;
    }

    public ArrayList<String> getAlternative_names() {
        return alternative_names;
    }

    public void setAlternative_names(ArrayList<String> alternative_names) {
        this.alternative_names = alternative_names;
    }

    public String getCountry_of_origin() {
        return country_of_origin;
    }

    public void setCountry_of_origin(String country_of_origin) {
        this.country_of_origin = country_of_origin;
    }

    public String getFci_classification() {
        return fci_classification;
    }

    public void setFci_classification(String fci_classification) {
        this.fci_classification = fci_classification;
    }

    public String getCountry_of_patronage() {
        return country_of_patronage;
    }

    public void setCountry_of_patronage(String country_of_patronage) {
        this.country_of_patronage = country_of_patronage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(breed);
        parcel.writeString(breed_id);
        parcel.writeStringList(life_span);
        parcel.writeString(thumbnail);
        parcel.writeString(percentage);
        parcel.writeStringList(temperament);
        parcel.writeStringList(utilization);
        parcel.writeStringList(ideal_height);
        parcel.writeStringList(ideal_weight);
        parcel.writeString(brief_history);
        parcel.writeByte((byte) (additional_info ? 1 : 0));
        parcel.writeStringList(alternative_names);
        parcel.writeString(country_of_origin);
        parcel.writeString(fci_classification);
        parcel.writeString(country_of_patronage);
    }
}
