package com.example.nasaspacesight.ViewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.POJO_NIL.Collection;
import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;

import java.util.List;
import java.util.Map;

/***
 * till now this model is used in the results figment
 */
public class ItemListViewModelNIL extends ViewModel {

    MainImageSearchRepo repo;

    public ItemListViewModelNIL() {
        repo = MainImageSearchRepo.getInstance();
        //Items=new MutableLiveData<>();
    }

    public LiveData<DataWrapper<Collection>> getItems() {
        return repo.NILgetItems();
    }

    public void SearchForImages(Map<String,Object> map)
    {
        repo.NILSearchForImagesString(map);
    }




}

