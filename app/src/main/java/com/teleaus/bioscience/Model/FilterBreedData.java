package com.teleaus.bioscience.Model;

import java.util.ArrayList;

public class FilterBreedData {
    private String breed_id;
    private String breed;
    private String thumbnail;
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

    public FilterBreedData() {
    }

    public FilterBreedData(String breed_id, String breed, String thumbnail, String country_of_origin, String brief_history, ArrayList<String> life_span, ArrayList<String> ideal_weight, ArrayList<String> ideal_height, ArrayList<String> temperament, String country_of_patronage, ArrayList<String> utilization, ArrayList<String> alternative_names, String fci_classification) {
        this.breed_id = breed_id;
        this.breed = breed;
        this.thumbnail = thumbnail;
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

    public String getBreed_id() {
        return breed_id;
    }

    public void setBreed_id(String breed_id) {
        this.breed_id = breed_id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
}
