package com.example.nasaspacesight.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.POJO_NIL.Collection;
import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;

import java.util.List;
import java.util.Map;

public class ItemListViewModelAPOD extends ViewModel
{
    MainImageSearchRepo repo;

    public ItemListViewModelAPOD() {
        repo = MainImageSearchRepo.getInstance();
        //Items=new MutableLiveData<>();
    }

    public LiveData<DataWrapper<List<SingleApodResponse>>> getItems()
    {
        return repo.APODgetItems();
    }

    public void searchForImages(Map<String,Object> map)
    {
        repo.APODsearchForImages(map);
    }



}
