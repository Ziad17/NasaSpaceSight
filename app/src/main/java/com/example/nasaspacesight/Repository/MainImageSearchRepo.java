package com.example.nasaspacesight.Repository;

import androidx.lifecycle.LiveData;

import com.example.nasaspacesight.ApiData.NasaClient;
import com.example.nasaspacesight.POJO.Collection;
import com.example.nasaspacesight.POJO.ImageLinks;
import com.example.nasaspacesight.POJO.Item;

import java.util.ArrayList;

public class MainImageSearchRepo {

    private static volatile MainImageSearchRepo mainImageSearchRepo;
    private NasaClient nasaClient;


    private MainImageSearchRepo()
    {
        nasaClient=NasaClient.getInstance();

    }

    public static MainImageSearchRepo getInstance()
    {
        if(mainImageSearchRepo==null)
        {
            synchronized (MainImageSearchRepo.class)
            {
                mainImageSearchRepo=new MainImageSearchRepo();
            }
        }
        return mainImageSearchRepo;
    }
    public LiveData<Collection> getItems()
    {
        return nasaClient.getItems();
    }

    public void SearchForImagesString(String textQuery, String keyWordsSplitByComma, String description, String location, String nasaId, String photographer, String title, Integer year_start, Integer year_end, Integer pageNumber)
    {
        nasaClient.SearchForImages(textQuery,keyWordsSplitByComma,description,location,nasaId,photographer,title,year_start,year_end,pageNumber);
    }

    public void SearchForImagesString(String textQuery, String keyWordsSplitByComma, String description, String location, String nasaId, String photographer, String title, Integer pageNumber) {
        nasaClient.SearchForImages(textQuery,keyWordsSplitByComma,description,location,nasaId,photographer,title,pageNumber);

    }


    public void searchLinksOfItem(String href)
    {
        nasaClient.searchLinksOfItem(href);
    }

    public LiveData<ImageLinks> getLinksOfItem() {
        return nasaClient.getLinksOfItem();
    }
}
