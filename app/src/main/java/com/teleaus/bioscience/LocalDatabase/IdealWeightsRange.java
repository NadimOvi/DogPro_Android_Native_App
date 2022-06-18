package com.teleaus.bioscience.LocalDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IdealWeightsRange {
    @SerializedName("All ranges")
    @Expose
    private List<Integer> allRanges = null;

    @SerializedName("3.0 to 52.0 lbs")
    @Expose
    private List<Integer> _30To520Lbs = null;
    @SerializedName("52.0 to 101.0 lbs")
    @Expose
    private List<Integer> _520To1010Lbs = null;
    @SerializedName("101.0 to 150.0 lbs")
    @Expose
    private List<Integer> _1010To1500Lbs = null;
    @SerializedName("150.0 to 200.0 lbs")
    @Expose
    private List<Integer> _1500To2000Lbs = null;

    public IdealWeightsRange() {
    }

    public IdealWeightsRange(List<Integer> allRanges, List<Integer> _30To520Lbs, List<Integer> _520To1010Lbs, List<Integer> _1010To1500Lbs, List<Integer> _1500To2000Lbs) {
        this.allRanges = allRanges;
        this._30To520Lbs = _30To520Lbs;
        this._520To1010Lbs = _520To1010Lbs;
        this._1010To1500Lbs = _1010To1500Lbs;
        this._1500To2000Lbs = _1500To2000Lbs;
    }

    public List<Integer> getAllRanges() {
        return allRanges;
    }

    public void setAllRanges(List<Integer> allRanges) {
        this.allRanges = allRanges;
    }

    public List<Integer> get30To520Lbs() {
        return _30To520Lbs;
    }

    public void set30To520Lbs(List<Integer> _30To520Lbs) {
        this._30To520Lbs = _30To520Lbs;
    }

    public List<Integer> get520To1010Lbs() {
        return _520To1010Lbs;
    }

    public void set520To1010Lbs(List<Integer> _520To1010Lbs) {
        this._520To1010Lbs = _520To1010Lbs;
    }

    public List<Integer> get1010To1500Lbs() {
        return _1010To1500Lbs;
    }

    public void set1010To1500Lbs(List<Integer> _1010To1500Lbs) {
        this._1010To1500Lbs = _1010To1500Lbs;
    }

    public List<Integer> get1500To2000Lbs() {
        return _1500To2000Lbs;
    }

    public void set1500To2000Lbs(List<Integer> _1500To2000Lbs) {
        this._1500To2000Lbs = _1500To2000Lbs;
    }

}
