package com.example.nasaspacesight.ApiData.APOD;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

import com.example.nasaspacesight.PojoModels.POJO_APOD.SingleApodResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApodClientAPI {
    String APOD_BASE_URL="https://api.nasa.gov/planetary/";



    String APOD_DATE="date"; //YYYY-MM-DD
    String APOD_HD="hd"; //bool
    String APOD_API_KEY="api_key"; //DEMO_KEY
    String APOD_API_KEY_VALUE="DEMO_KEY";
    String APOD_START_DATE="start_date"; //1996 is the limit
    String APOD_END_DATE="end_date";



    @GET("apod")
    Call<List<SingleApodResponse>> getImages(@Nullable @QueryMap Map<String,Object> options);


    @GET("apod")
    Call<SingleApodResponse> getImage(@Nullable @QueryMap Map<String,Object> options);



}
