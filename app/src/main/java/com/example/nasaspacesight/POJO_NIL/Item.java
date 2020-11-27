package com.example.nasaspacesight.POJO_NIL;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;


public class Item extends Observable implements Serializable {




    @SerializedName("data")
    @Expose
    private List<Datum> data;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("links")
    @Expose
    private List<Link> links;
    @Expose
    private boolean Favorite;


    public String getNasa_id() {
        return nasa_id;
    }

    public void setNasa_id(String nasa_id) {
        this.nasa_id = nasa_id;
    }

    private String nasa_id;


    protected Item(Parcel in) {
        href = in.readString();
    }


    public Item(){}


    public Datum getData() {
        return data.get(0);
        //To get The First Data object as there will be only one in most of the cases
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Link getLinks() {
        return links.get(0);
        //To get The First Link object as there will be only one in most of the cases


    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }



    @Override
    public String toString() {
        return "Item{" +
                "data=" + data +
                ", href='" + href + '\'' +
                ", links=" + links +
                '}';
    }

    public boolean isFavorite() {
        return Favorite;
    }

    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }


}
