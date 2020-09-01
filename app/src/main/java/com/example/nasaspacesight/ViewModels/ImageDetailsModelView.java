package com.example.nasaspacesight.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.POJO.ImageLinks;
import com.example.nasaspacesight.POJO.Item;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;

import java.util.HashMap;

public class ImageDetailsModelView extends ViewModel {
    private MainImageSearchRepo repo;



    public ImageDetailsModelView() {
        this.repo = MainImageSearchRepo.getInstance();
    }


    public LiveData<ImageLinks> getLinksOfItem() {
        return repo.getLinksOfItem();
    }

    public void searchLinksOfItem(String href)
    {
        repo.searchLinksOfItem(href);
    }
}
