package com.example.nasaspacesight.PojoModels;


/***
 * A Wrapper Class to encapsulate the network response inside the live data to facilitate dealing with UI components
 * @param <T>
 */
public class DataWrapper<T> extends DataWrapperStatus {

    private T collection;


    public T getCollection() {
        return collection;
    }

    public DataWrapper(T collection, int status, String msg) {
        this.status = status;
        this.message = msg;
        this.collection = collection;
    }

}
