package com.example.nasaspacesight.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.POJO_NIL.Collection;
import com.example.nasaspacesight.POJO_NIL.ImageLinks;
import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.POJO_NIL.ItemOffline;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;
import com.example.nasaspacesight.Room.RoomDatabase;

import java.util.List;

public class ImageDetailsModelViewNIL extends ViewModel {
    private MainImageSearchRepo repo;



    public ImageDetailsModelViewNIL() {
        this.repo = MainImageSearchRepo.getInstance();
    }


    public LiveData<ImageLinks> getLinksOfItem() {
        return repo.getLinksOfItem();
    }

    public void searchLinksOfItem(String href)
    {
        repo.NILSearchLinksOfItem(href);
    }

    public void removeImage(SingleApodResponse image, RoomDatabase database)
    {
        repo.removeImage(image,database);
    }

    public void saveImage(SingleApodResponse image,RoomDatabase roomDatabase)
    {
        repo.saveImage(image,roomDatabase);
    }
    public void saveImage(Item image,RoomDatabase roomDatabase)
    {
        repo.saveImage(image,roomDatabase);
    }

    public void removeImage(Item image,RoomDatabase roomDatabase)
    {
        repo.removeImage(image,roomDatabase);
    }

    public void searchCacheAPOD(RoomDatabase database)
    {
         repo.requestAPODcache(database);
    }
    public void searchCacheNIL(RoomDatabase database)
    {
         repo.requestNILcache(database);
    }
    public LiveData<DataWrapper<Collection>> getNILitems()
    {
         return repo.getNILcache();
    }
    public LiveData<DataWrapper<List<SingleApodResponse>>> getAPODitems()
    {
        return repo.getAPODcache();
    }

    public void checkNILItem(String nasa_id,RoomDatabase database)
    {
         repo.checkNILItem(nasa_id,database);
    }
    public void checkAPODItem(String date,RoomDatabase database)
    {
        repo.checkAPODItem(date,database);
    }
    public LiveData<ItemOffline> getNILItem()
    {
        return repo.getNILItem();
    }
    public LiveData<SingleApodResponse> getAPODItem()
    {
        return repo.getAPODItem();
    }
}
