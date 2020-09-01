package com.example.nasaspacesight.ApiData;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nasaspacesight.Executors.AppExecutors;
import com.example.nasaspacesight.POJO.Collection;
import com.example.nasaspacesight.POJO.FullApiResponse;
import com.example.nasaspacesight.POJO.ImageLinks;
import com.example.nasaspacesight.POJO.Item;
import com.example.nasaspacesight.POJO.NullLinks;
import com.example.nasaspacesight.Util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.nasaspacesight.Util.Constants.HTTP_OK_CODE;

public class NasaClient {
    private static volatile NasaClient nasaClient;
    private MutableLiveData<Collection> Items;
    private static final String TAG = "NasaClient";
    private MutableLiveData<ImageLinks> links;


    private NasaClient() {
        //init items
        Items = new MutableLiveData<>();
        links = new MutableLiveData<>();

    }

    public static NasaClient getInstance() {
        if (nasaClient == null) {
            synchronized (NasaClient.class) {
                nasaClient = new NasaClient();
            }
        }
        return nasaClient;
    }

    public LiveData<Collection> getItems() {
        return Items;
    }

    public LiveData<ImageLinks> getLinksOfItem() {
        return links;
    }

    public void SearchForImages(String textQuery,
                                String keyWordsSplitByComma,
                                String Description,
                                String location,
                                String nasaId,
                                String photographer,
                                String Title,
                                Integer pageNumber) {
        final Future handler = AppExecutors.getInstance().getworkIO().submit(new RetrieveResults(textQuery, Description, location, Title, photographer, keyWordsSplitByComma, pageNumber));
        AppExecutors.getInstance().getworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //return timeout error
                handler.cancel(true);
            }
        }, Constants.NETWORK_REQUEST_TIME_OUT_IN_MS, TimeUnit.MILLISECONDS);
    }

    public void SearchForImages(String textQuery,
                                String keyWordsSplitByComma,
                                String Description,
                                String location,
                                String nasaId,
                                String photographer,
                                String Title,
                                Integer year_start,
                                Integer year_end,
                                Integer pageNumber
    ) {
        final Future handler = AppExecutors.getInstance().getworkIO().submit(new RetrieveResults(textQuery, Description, location, Title, photographer, keyWordsSplitByComma, year_start, year_end, pageNumber));
        AppExecutors.getInstance().getworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //return timeout error
                handler.cancel(true);
            }
        }, Constants.NETWORK_REQUEST_TIME_OUT_IN_MS, TimeUnit.MILLISECONDS);
    }


    public void searchLinksOfItem(final String href) {
        links.postValue(new NullLinks());

        AppExecutors.getInstance().getworkIO().submit(new Runnable() {
            @Override
            public void run() {


                try {
                    Log.e(TAG, "run: 1" );
                    links.postValue(new NullLinks());
                    Response response = NasaResources.getInstance().connect().getImageLinks(href).execute();
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
                    Log.e(TAG, "run: 2" );

                }
            }
        });
    }


    private class RetrieveResults implements Runnable {
        String query;
        String description;
        String location;
        String title;
        String photographer;
        String keywords;
        Integer endYear;
        Integer startYear;
        Integer pageNumber;

        boolean YEAR_SEARCH;
        boolean CANCELED;

        public RetrieveResults(String query, String description, String location, String title, String photographer, String keywords, Integer _pageNumber) {
            this.query = query;
            this.description = description;
            this.location = location;
            this.title = title;
            this.photographer = photographer;
            this.keywords = keywords;
            this.pageNumber = _pageNumber;
            YEAR_SEARCH = false;
            CANCELED = false;
        }

        public RetrieveResults(String query, String description, String location, String title, String photographer, String keywords, Integer endYear, Integer startYear, Integer _pageNumber) {
            this.query = query;
            this.description = description;
            this.location = location;
            this.title = title;
            this.photographer = photographer;
            this.keywords = keywords;
            this.endYear = endYear;
            this.startYear = startYear;
            this.pageNumber = _pageNumber;

            CANCELED = false;

            YEAR_SEARCH = true;
        }

        private Call<FullApiResponse> Search() {
            return YEAR_SEARCH ? NasaResources.getInstance().connect().Search(query, keywords, description, location, null, photographer, title, pageNumber,"image") :
                    NasaResources.getInstance().connect().Search(query, keywords, description, location, null, photographer, title, startYear, endYear, pageNumber,"image");

        }

        @Override
        public void run() {
            try {
                Response response = Search().execute();
                if (CANCELED) {
                    return;
                }
                if (response.code() == HTTP_OK_CODE) {

                    FullApiResponse fullApiResponse = (FullApiResponse) response.body();
                    Collection items = fullApiResponse.getCollection();
                    Items.postValue(null);
                    Items.postValue(items);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Items.postValue(null);
            }


        }

        public void cancelRequest() {
            CANCELED = true;

        }
    }


}




