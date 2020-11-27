
package com.example.nasaspacesight.POJO_APOD;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.nasaspacesight.Room.DatabaseInfo;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Observable;

import static com.example.nasaspacesight.Room.DatabaseInfo.ApodTable.APOD_OBJECT_COPYRIGHT;
import static com.example.nasaspacesight.Room.DatabaseInfo.ApodTable.APOD_OBJECT_DATE;
import static com.example.nasaspacesight.Room.DatabaseInfo.ApodTable.APOD_OBJECT_EXPLAINATION;
import static com.example.nasaspacesight.Room.DatabaseInfo.ApodTable.APOD_OBJECT_HDURL;
import static com.example.nasaspacesight.Room.DatabaseInfo.ApodTable.APOD_OBJECT_IS_FAV;
import static com.example.nasaspacesight.Room.DatabaseInfo.ApodTable.APOD_OBJECT_MEDIA_TYPE;
import static com.example.nasaspacesight.Room.DatabaseInfo.ApodTable.APOD_OBJECT_TITLE;
import static com.example.nasaspacesight.Room.DatabaseInfo.ApodTable.APOD_OBJECT_URL;


@Entity
public class SingleApodResponse extends Observable implements Serializable {
    @ColumnInfo(name = APOD_OBJECT_COPYRIGHT)
    @SerializedName("copyright")
    private String mCopyright;
    @PrimaryKey
    @NonNull

    @ColumnInfo(name = APOD_OBJECT_DATE)
    @SerializedName("date")
    private String mDate;

    @ColumnInfo(name = APOD_OBJECT_EXPLAINATION)

    @SerializedName("explanation")
    private String mExplanation;
    @ColumnInfo(name = APOD_OBJECT_HDURL)

    @SerializedName("hdurl")
    private String mHdurl;
    @ColumnInfo(name = APOD_OBJECT_MEDIA_TYPE)
    @SerializedName("media_type")
    private String mMediaType;
    @Ignore
    @SerializedName("service_version")
    private String mServiceVersion;
    @ColumnInfo(name = APOD_OBJECT_TITLE)

    @SerializedName("title")
    private String mTitle;
    @ColumnInfo(name = APOD_OBJECT_URL)

    @SerializedName("url")
    private String mUrl;


    protected SingleApodResponse(Parcel in) {
        mCopyright = in.readString();
        mDate = in.readString();
        mExplanation = in.readString();
        mHdurl = in.readString();
        mMediaType = in.readString();
        mServiceVersion = in.readString();
        mTitle = in.readString();
        mUrl = in.readString();
        isFav = in.readByte() != 0;
    }



    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
    @ColumnInfo(name = APOD_OBJECT_IS_FAV)
    private boolean isFav;

    public SingleApodResponse(String mDate, String mTitle, String mUrl) {
        this.mDate = mDate;
        this.mTitle = mTitle;
        this.mUrl = mUrl;
    }


    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        mCopyright = copyright;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getExplanation() {
        return mExplanation;
    }

    public void setExplanation(String explanation) {
        mExplanation = explanation;
    }

    public String getHdurl() {
        return mHdurl;
    }

    public void setHdurl(String hdurl) {
        mHdurl = hdurl;
    }

    public String getMediaType() {
        return mMediaType;
    }

    public void setMediaType(String mediaType) {
        mMediaType = mediaType;
    }

    public String getServiceVersion() {
        return mServiceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        mServiceVersion = serviceVersion;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }


}
