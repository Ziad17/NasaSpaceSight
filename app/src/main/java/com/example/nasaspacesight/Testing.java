package com.example.nasaspacesight;

import com.example.nasaspacesight.ApiData.APOD.ApodClient;
import com.example.nasaspacesight.ApiData.APOD.ApodClientAPI;
import com.example.nasaspacesight.ApiData.APOD.ApodResources;

import java.util.HashMap;

import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Testing {
    public static void main(String[] args)
    {

        HashMap<String,Object> searchHashMap=new HashMap<String,Object>();


        searchHashMap.put(ApodClientAPI.APOD_API_KEY,ApodClientAPI.APOD_API_KEY_VALUE);
        searchHashMap.put(ApodClientAPI.APOD_DATE,"2020-10-1");


        //ApodClient.getInstance().searchForImage(searchHashMap);


    }
}
