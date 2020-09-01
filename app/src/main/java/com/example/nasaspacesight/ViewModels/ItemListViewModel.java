package com.example.nasaspacesight.ViewModels;


import android.widget.ImageButton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.POJO.Collection;
import com.example.nasaspacesight.POJO.ImageLinks;
import com.example.nasaspacesight.POJO.Item;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;

import java.util.ArrayList;
import java.util.List;

/***
 * till now this model is used in the results figment
 */
public class ItemListViewModel extends ViewModel {

    MainImageSearchRepo repo;

    public ItemListViewModel() {
        repo = MainImageSearchRepo.getInstance();
        //Items=new MutableLiveData<>();
    }

    public LiveData<Collection> getItems() {
        return repo.getItems();
    }

    public void SearchForImages(String textQuery,
                                String keyWordsSplitByComma,
                                String Description,
                                String location,
                                String nasaId,
                                String photographer,
                                String Title,
                                Integer pageNumber)
    {
        repo.SearchForImagesString(textQuery,keyWordsSplitByComma,Description,location,nasaId,photographer,Title,pageNumber);
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
                                Integer pageNumber)
    {
        repo.SearchForImagesString(textQuery,keyWordsSplitByComma,Description,location,nasaId,photographer,Title,year_start,year_end,pageNumber);
    }




}

