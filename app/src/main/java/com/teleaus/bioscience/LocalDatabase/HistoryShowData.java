package com.teleaus.bioscience.LocalDatabase;

public class HistoryShowData {
    private int id;
    private String image_path;
    private String request_fulfilled_at;
    private HistoryShowResponseModel response;
    private String created_at;
    private String updated_at;
    private String image_url;

    public HistoryShowData() {
    }

    public HistoryShowData(int id, String image_path, String request_fulfilled_at, HistoryShowResponseModel response, String created_at, String updated_at, String image_url) {
        this.id = id;
        this.image_path = image_path;
        this.request_fulfilled_at = request_fulfilled_at;
        this.response = response;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.image_url = image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getRequest_fulfilled_at() {
        return request_fulfilled_at;
    }

    public void setRequest_fulfilled_at(String request_fulfilled_at) {
        this.request_fulfilled_at = request_fulfilled_at;
    }

    public HistoryShowResponseModel getResponse() {
        return response;
    }

    public void setResponse(HistoryShowResponseModel response) {
        this.response = response;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
