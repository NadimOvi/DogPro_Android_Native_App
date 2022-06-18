package com.teleaus.bioscience.Model;

import java.util.ArrayList;

public class FilterModelClass {
    ArrayList<FilterBreedData> breeds;

    public FilterModelClass() {
    }

    public FilterModelClass(ArrayList<FilterBreedData> breeds) {
        this.breeds = breeds;
    }

    public ArrayList<FilterBreedData> getBreeds() {
        return breeds;
    }

    public void setBreeds(ArrayList<FilterBreedData> breeds) {
        this.breeds = breeds;
    }
}
