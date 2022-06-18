package com.teleaus.bioscience.Model;

public class Associated_diseases {
    String name;
    String other_name;
    String mode_of_inheritance;
    String omia_link;
    String gene_link;

    public Associated_diseases() {
    }

    public Associated_diseases(String name, String other_name, String mode_of_inheritance, String omia_link, String gene_link) {
        this.name = name;
        this.other_name = other_name;
        this.mode_of_inheritance = mode_of_inheritance;
        this.omia_link = omia_link;
        this.gene_link = gene_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getMode_of_inheritance() {
        return mode_of_inheritance;
    }

    public void setMode_of_inheritance(String mode_of_inheritance) {
        this.mode_of_inheritance = mode_of_inheritance;
    }

    public String getOmia_link() {
        return omia_link;
    }

    public void setOmia_link(String omia_link) {
        this.omia_link = omia_link;
    }

    public String getGene_link() {
        return gene_link;
    }

    public void setGene_link(String gene_link) {
        this.gene_link = gene_link;
    }
}
