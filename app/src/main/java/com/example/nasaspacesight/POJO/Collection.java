package com.example.nasaspacesight.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Collection {

    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("items")
    @Expose
    private ArrayList<Item> items = null;
    @SerializedName("version")
    @Expose
    private String version;

    public Collection() {
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;

    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}