package com.example.nasaspacesight.ApiData.Images;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nasaspacesight.Executors.AppExecutors;
import com.example.nasaspacesight.PojoModels.POJO_NIL.Collection;
import com.example.nasaspacesight.PojoModels.POJO_NIL.FullApiResponse;
import com.example.nasaspacesight.PojoModels.POJO_NIL.ImageLinks;
import com.example.nasaspacesight.PojoModels.DataWrapper;

import com.example.nasaspacesight.PojoModels.POJO_NIL.NullLinks;
import com.example.nasaspacesight.Util.Constants;
import com.example.nasaspacesight.PojoModels.DataWrapperStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Response;

import static com.example.nasaspacesight.PojoModels.DataWrapperStatus.FAILED;
import static com.example.nasaspacesight.Util.Constants.HTTP_OK_CODE;
import static com.example.nasaspacesight.PojoModels.DataWrapperStatus.SUCCESSED;

public class ImagesClient {
    private static volatile ImagesClient imagesClient;
    private MutableLiveData<DataWrapper<Collection>> Items;
    private static final String TAG = "ImagesClient";
    private MutableLiveData<ImageLinks> links;


    private ImagesClient() {
        //init items
        Items = new MutableLiveData<DataWrapper<Collection>>();
        links = new MutableLiveData<>();

    }

    public static ImagesClient getInstance() {
        if (imagesClient == null) {
            synchronized (ImagesClient.class) {
                imagesClient = new ImagesClient();
            }
        }
        return imagesClient;
    }

    public LiveData<DataWrapper<Collection>> getItems() {
        return Items;
    }

    public LiveData<ImageLinks> getLinksOfItem() {
        return links;
    }

    public void SearchForImages(final Map<String,Object> map) {

        Log.e(TAG, "SearchForImages: "+map.toString() );
        final Future handler = AppExecutors.getInstance().getworkIO().submit(() -> {
                try {

                    boolean CANCELED=false;
                    Response response = ImageResources.getInstance().connect().Search(map).execute();
                    if (CANCELED) {
                        Items.postValue(new DataWrapper<>(Items.getValue().getCollection(), DataWrapperStatus.CANCELED,"Request Canceled"));
                        return;
                    }
                    if (response.code() == HTTP_OK_CODE) {
                        FullApiResponse fullApiResponse = (FullApiResponse) response.body();
                        Collection collection = fullApiResponse.getCollection();
                        Items.postValue(new DataWrapper<>(collection,SUCCESSED,null));
                    }

                } catch (IOException e) {
                    Log.e(TAG, "SearchForImages: ",e );
                    Items.postValue(new DataWrapper<>(Items.getValue().getCollection(),FAILED,"Connection Is Down"));
                }

        });

        AppExecutors.getInstance().getworkIO().schedule(() -> {
            handler.cancel(true);
        }, Constants.NETWORK_REQUEST_TIME_OUT_IN_MS, TimeUnit.MILLISECONDS);
    }




    public void searchLinksOfItem(final String href) {
        links.postValue(new NullLinks());
        AppExecutors.getInstance().getworkIO().submit(() -> {
            try {
                links.postValue(new NullLinks());
                Response response = ImageResources.getInstance().connect().getImageLinks(href).execute();
                if (response.code() == HTTP_OK_CODE) {
                    ArrayList<String> linksList = (ArrayList<String>) response.body();
                    ImageLinks imageLinks = new ImageLinks(linksList);
                    if (imageLinks.ORIGINAL != null)
                        links.postValue(imageLinks);
                    else {
                        links.postValue(null);
                    }
                } else {
                    links.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                links.postValue(null);

            }
        });
    }





}




