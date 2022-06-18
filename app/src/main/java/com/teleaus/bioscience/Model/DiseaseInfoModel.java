package com.teleaus.bioscience.Model;

import java.util.ArrayList;
import java.util.List;

public class DiseaseInfoModel {
    String name;
    String alternative_name;
    String cause;
    String diagnosis;
    String symptom;
    String treat_method;
    String breeder_advice;
    String source_link;
    String description;
    ArrayList<Associated_diseases> associated_diseases;

    public DiseaseInfoModel() {
    }

    public DiseaseInfoModel(String name, String alternative_name, String cause, String diagnosis, String symptom, String treat_method, String breeder_advice, String source_link, String description, ArrayList<Associated_diseases> associated_diseases) {
        this.name = name;
        this.alternative_name = alternative_name;
        this.cause = cause;
        this.diagnosis = diagnosis;
        this.symptom = symptom;
        this.treat_method = treat_method;
        this.breeder_advice = breeder_advice;
        this.source_link = source_link;
        this.description = description;
        this.associated_diseases = associated_diseases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternative_name() {
        return alternative_name;
    }

    public void setAlternative_name(String alternative_name) {
        this.alternative_name = alternative_name;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getTreat_method() {
        return treat_method;
    }

    public void setTreat_method(String treat_method) {
        this.treat_method = treat_method;
    }

    public String getBreeder_advice() {
        return breeder_advice;
    }

    public void setBreeder_advice(String breeder_advice) {
        this.breeder_advice = breeder_advice;
    }

    public String getSource_link() {
        return source_link;
    }

    public void setSource_link(String source_link) {
        this.source_link = source_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Associated_diseases> getAssociated_diseases() {
        return associated_diseases;
    }

    public void setAssociated_diseases(ArrayList<Associated_diseases> associated_diseases) {
        this.associated_diseases = associated_diseases;
    }
}
