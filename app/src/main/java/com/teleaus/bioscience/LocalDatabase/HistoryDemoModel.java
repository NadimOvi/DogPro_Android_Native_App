package com.teleaus.bioscience.LocalDatabase;

public class HistoryDemoModel {
    private long id;
    private String ids;

    public HistoryDemoModel(String ids) {
        this.id = id;
        this.ids = ids;
    }

    public HistoryDemoModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
