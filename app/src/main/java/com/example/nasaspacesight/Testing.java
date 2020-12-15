package com.example.nasaspacesight;

import com.example.nasaspacesight.ApiData.APOD.ApodClientAPI;

import java.util.HashMap;

@Deprecated
public class Testing {
    public static void main(String[] args)
    {

        HashMap<String,Object> searchHashMap=new HashMap<String,Object>();


        searchHashMap.put(ApodClientAPI.APOD_API_KEY,ApodClientAPI.APOD_API_KEY_VALUE);
        searchHashMap.put(ApodClientAPI.APOD_DATE,"2020-10-1");


        //ApodClient.getInstance().searchForImage(searchHashMap);


    }
}
