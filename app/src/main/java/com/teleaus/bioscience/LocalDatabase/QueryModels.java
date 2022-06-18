package com.teleaus.bioscience.LocalDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryModels {
    HashMap<String, List<Integer>> ideal_weights_range;
    HashMap<String, List<Integer>> ideal_heights_range;


    ArrayList<String> temperaments;
    ArrayList<String> utilization;

    public QueryModels() {
    }

    public QueryModels(HashMap<String, List<Integer>> ideal_weights_range, HashMap<String, List<Integer>> ideal_heights_range, ArrayList<String> temperaments, ArrayList<String> utilization) {
        this.ideal_weights_range = ideal_weights_range;
        this.ideal_heights_range = ideal_heights_range;
        this.temperaments = temperaments;
        this.utilization = utilization;
    }

    public HashMap<String, List<Integer>> getIdeal_weights_range() {
        return ideal_weights_range;
    }

    public void setIdeal_weights_range(HashMap<String, List<Integer>> ideal_weights_range) {
        this.ideal_weights_range = ideal_weights_range;
    }

    public HashMap<String, List<Integer>> getIdeal_heights_range() {
        return ideal_heights_range;
    }

    public void setIdeal_heights_range(HashMap<String, List<Integer>> ideal_heights_range) {
        this.ideal_heights_range = ideal_heights_range;
    }

    public ArrayList<String> getTemperaments() {
        return temperaments;
    }

    public void setTemperaments(ArrayList<String> temperaments) {
        this.temperaments = temperaments;
    }

    public ArrayList<String> getUtilization() {
        return utilization;
    }

    public void setUtilization(ArrayList<String> utilization) {
        this.utilization = utilization;
    }
}
