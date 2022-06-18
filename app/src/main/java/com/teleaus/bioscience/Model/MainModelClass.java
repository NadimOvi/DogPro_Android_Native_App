package com.teleaus.bioscience.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class MainModelClass implements Serializable {

    private MainResponseModelClass response;
    private String id;
    private String image_path;
    private String request_fulfilled_at;
    private String created_at;
    private String updated_at;
    private String image_url;

    public MainModelClass() {
    }

    public MainModelClass(MainResponseModelClass response, String id, String image_path, String request_fulfilled_at, String created_at, String updated_at, String image_url) {
        this.response = response;
        this.id = id;
        this.image_path = image_path;
        this.request_fulfilled_at = request_fulfilled_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.image_url = image_url;
    }

    public MainResponseModelClass getResponse() {
        return response;
    }

    public void setResponse(MainResponseModelClass response) {
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
