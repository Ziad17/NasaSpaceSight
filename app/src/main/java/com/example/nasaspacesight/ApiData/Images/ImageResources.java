package com.example.nasaspacesight.ApiData.Images;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.nasaspacesight.ApiData.Images.ImagesClientAPI.NASA_IMAGES_BASE_URL;


public class ImageResources {

    /**
     * The Full Documentation of the Nasa API used -->> https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf
     *
     */
    private static volatile ImageResources client;
    private Retrofit retrofit;
    private ImageResources()
    {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();


            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);



        retrofit= new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NASA_IMAGES_BASE_URL)
                .client(builder.build())
                .build();

    }
    public static ImageResources getInstance()
    {
        if(client==null)
        {
            synchronized (ImageResources.class)
            {
                client=new ImageResources();
            }
        }
        return client;
    }
    public ImagesClientAPI connect()
    {
        return retrofit.create(ImagesClientAPI.class);
    }
}
