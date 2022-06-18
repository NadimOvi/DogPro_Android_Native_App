package com.teleaus.bioscience.LocalDatabase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IdealHeightsRange {
    @SerializedName("All ranges")
    @Expose
    private List<Integer> allRanges = null;
    @SerializedName("5.0 to 12.0 inches")
    @Expose
    private List<Integer> _50To120Inches = null;
    @SerializedName("12.0 to 19.0 inches")
    @Expose
    private List<Integer> _120To190Inches = null;
    @SerializedName("19.0 to 26.0 inches")
    @Expose
    private List<Integer> _190To260Inches = null;
    @SerializedName("26.0 to 35.0 inches")
    @Expose
    private List<Integer> _260To350Inches = null;

    public IdealHeightsRange() {
    }

    public IdealHeightsRange(List<Integer> allRanges, List<Integer> _50To120Inches, List<Integer> _120To190Inches, List<Integer> _190To260Inches, List<Integer> _260To350Inches) {
        this.allRanges = allRanges;
        this._50To120Inches = _50To120Inches;
        this._120To190Inches = _120To190Inches;
        this._190To260Inches = _190To260Inches;
        this._260To350Inches = _260To350Inches;
    }


    public List<Integer> getAllRanges() {
        return allRanges;
    }

    public void setAllRanges(List<Integer> allRanges) {
        this.allRanges = allRanges;
    }

    public List<Integer> get50To120Inches() {
        return _50To120Inches;
    }

    public void set50To120Inches(List<Integer> _50To120Inches) {
        this._50To120Inches = _50To120Inches;
    }

    public List<Integer> get120To190Inches() {
        return _120To190Inches;
    }

    public void set120To190Inches(List<Integer> _120To190Inches) {
        this._120To190Inches = _120To190Inches;
    }

    public List<Integer> get190To260Inches() {
        return _190To260Inches;
    }

    public void set190To260Inches(List<Integer> _190To260Inches) {
        this._190To260Inches = _190To260Inches;
    }

    public List<Integer> get260To350Inches() {
        return _260To350Inches;
    }

    public void set260To350Inches(List<Integer> _260To350Inches) {
        this._260To350Inches = _260To350Inches;
    }


}
