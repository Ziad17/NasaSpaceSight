package com.example.nasaspacesight.POJO_NIL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FullApiResponse {

    @SerializedName("collection")
    @Expose
    private Collection collection;

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

}