package com.example.nasaspacesight.ApiData.EPIC;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.nasaspacesight.ApiData.EPIC.EpicClientAPI.EPIC_BASE_URL;

@Deprecated

public class EpicResources {
    private static volatile EpicResources client;
    private Retrofit retrofit;
    private EpicResources()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        retrofit=new Retrofit.Builder()
                .baseUrl(EPIC_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }
    public static EpicResources getInstance()
    {
        if(client==null)
        {
            synchronized (EpicResources.class)
            {
                client=new EpicResources();
            }
        }
        return client;
    }
    public EpicClientAPI connect()
    {
        return retrofit.create(EpicClientAPI.class);
    }
}
