package com.example.nasaspacesight.POJO_NIL;

import android.os.Parcel;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_CENTER;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_DATE_CREATED;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_DESCRIPTION;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_NASA_ID;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_PHOTOGRAPHER;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_DATA_TITLE;
import static com.example.nasaspacesight.Room.DatabaseInfo.NilTable.NIL_OBJECT_MEDIA_TYPE;

public class Datum implements Serializable {

    @ColumnInfo(name =NIL_OBJECT_DATA_TITLE )
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = NIL_OBJECT_DATA_NASA_ID)
    @SerializedName("nasa_id")
    @Expose
    private String nasaId;
    @ColumnInfo(name = NIL_OBJECT_DATA_CENTER)
    @SerializedName("center")
    @Expose
    private String center;
    @ColumnInfo(name = NIL_OBJECT_DATA_DATE_CREATED)
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @ColumnInfo(name = NIL_OBJECT_DATA_PHOTOGRAPHER)
    @SerializedName("photographer")
    @Expose
    private String photographer;
    @ColumnInfo(name = NIL_OBJECT_MEDIA_TYPE)
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @ColumnInfo(name =NIL_OBJECT_DATA_DESCRIPTION )
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

    public Datum()
    {}


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
