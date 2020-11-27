package com.example.nasaspacesight.ApiData.APOD;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApodResources {
    private static volatile ApodResources client;
    private Retrofit retrofit;

    private ApodResources()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        retrofit=new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApodClientAPI.APOD_BASE_URL)
                .build();
    }

    public static ApodResources getInstance()
    {
        if(client==null)
        {
            synchronized (ApodResources.class)
            {
                client=new ApodResources();
            }
        }
        return client;
    }
    public ApodClientAPI connect()
    {
        return retrofit.create(ApodClientAPI.class);
    }
}
