package com.example.nasaspacesight.PojoModels;

public abstract class DataWrapperStatus {

    //status
    public static final int LOADING=0;
    public static final int FAILED=1;
    public static final int SUCCESSED=2;
    public static final int CANCELED=3;
    protected int status;
    protected String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }



}

