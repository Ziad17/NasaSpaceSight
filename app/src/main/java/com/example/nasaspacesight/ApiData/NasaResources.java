package com.example.nasaspacesight.ApiData;

import com.example.nasaspacesight.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.nasaspacesight.Util.Constants.NASA_IMAGES_BASE_URL;

public class NasaResources {

    /**
     * The Full Documentation of the Nasa API used -->> https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf
     *
     */
    private static volatile NasaResources client;
    private Retrofit retrofit;
    private NasaResources()
    {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }


        retrofit= new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NASA_IMAGES_BASE_URL)
                .client(builder.build())
                .build();

    }
    public static NasaResources getInstance()
    {
        if(client==null)
        {
            synchronized (NasaResources.class)
            {
                client=new NasaResources();
            }
        }
        return client;
    }
    public NasaClientAPI connect()
    {
        return retrofit.create(NasaClientAPI.class);
    }
}
