package com.teleaus.bioscience.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class BreedHistoryData implements Parcelable {
    private int id;
    private String image_path;
    private String request_fulfilled_at;
    private BreedHistoryResponse response;
    private String created_at;
    private String updated_at;
    private String image_url;

    public BreedHistoryData() {
    }

    public BreedHistoryData(int id, String image_path, String request_fulfilled_at, BreedHistoryResponse response, String created_at, String updated_at, String image_url) {
        this.id = id;
        this.image_path = image_path;
        this.request_fulfilled_at = request_fulfilled_at;
        this.response = response;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.image_url = image_url;
    }

    protected BreedHistoryData(Parcel in) {
        id = in.readInt();
        image_path = in.readString();
        request_fulfilled_at = in.readString();
        response = in.readParcelable(BreedHistoryResponse.class.getClassLoader());
        created_at = in.readString();
        updated_at = in.readString();
        image_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(image_path);
        dest.writeString(request_fulfilled_at);
        dest.writeParcelable(response, flags);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(image_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BreedHistoryData> CREATOR = new Creator<BreedHistoryData>() {
        @Override
        public BreedHistoryData createFromParcel(Parcel in) {
            return new BreedHistoryData(in);
        }

        @Override
        public BreedHistoryData[] newArray(int size) {
            return new BreedHistoryData[size];
        }
    };

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

    public BreedHistoryResponse getResponse() {
        return response;
    }

    public void setResponse(BreedHistoryResponse response) {
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
