package com.example.nasaspacesight.POJO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("nasa_id")
    @Expose
    private String nasaId;
    @SerializedName("center")
    @Expose
    private String center;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("photographer")
    @Expose
    private String photographer;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("description")
    @Expose
    private String description;

    protected Datum(Parcel in) {
        title = in.readString();
        nasaId = in.readString();
        center = in.readString();
        dateCreated = in.readString();
        photographer = in.readString();
        mediaType = in.readString();
        description = in.readString();
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
