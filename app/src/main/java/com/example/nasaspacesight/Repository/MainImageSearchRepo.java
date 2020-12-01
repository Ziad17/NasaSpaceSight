package com.example.nasaspacesight.Repository;

import androidx.lifecycle.LiveData;

import com.example.nasaspacesight.ApiData.APOD.ApodClient;
import com.example.nasaspacesight.ApiData.Images.ImagesClient;
import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.POJO_NIL.Collection;
import com.example.nasaspacesight.POJO_NIL.ImageLinks;
import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.POJO_NIL.ItemOffline;
import com.example.nasaspacesight.Room.RoomClient;
import com.example.nasaspacesight.Room.RoomDatabase;
import com.example.nasaspacesight.ViewModels.DataWrapper;

import java.util.List;
import java.util.Map;

public class MainImageSearchRepo {

    private static volatile MainImageSearchRepo mainImageSearchRepo;
    private ImagesClient imagesClient;
    private ApodClient apodClient;
    private RoomClient roomDatabaseOperations;


    private MainImageSearchRepo()
    {
        imagesClient = ImagesClient.getInstance();
        apodClient=ApodClient.getInstance();
        roomDatabaseOperations=RoomClient.getInstance();
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
    public LiveData<DataWrapper<Collection>> NILgetItems()
    {
        return imagesClient.getItems();
    }

    public void NILSearchForImagesString(Map<String,Object> map) {
        imagesClient.SearchForImages(map);
    }

    public void NILSearchLinksOfItem(String href)
    {
        imagesClient.searchLinksOfItem(href);
    }

    public LiveData<ImageLinks> getLinksOfItem() {
        return imagesClient.getLinksOfItem();
    }





    public LiveData<DataWrapper<List<SingleApodResponse>>> APODgetItems()
    {
        return apodClient.getItems();
    }

    public void APODsearchForImages(Map<String,Object> map)
    {
        apodClient.searchForImages(map);

    }


    public void saveImage(Item image,RoomDatabase database)
    {
        roomDatabaseOperations.insert(image,database);
    }
    public void removeImage(Item image,RoomDatabase database)
    {
        roomDatabaseOperations.delete(image,database);

    }
    public void saveImage(SingleApodResponse image,RoomDatabase database)
    {
        roomDatabaseOperations.insert(image,database);

    }
    public void removeImage(SingleApodResponse image,RoomDatabase database)
    {
        roomDatabaseOperations.delete(image,database);

    }
    public LiveData<DataWrapper<Collection> >getNILcache()
    {
        return roomDatabaseOperations.getArrayNIL() ;
    }
    public LiveData<DataWrapper<List<SingleApodResponse>>> getAPODcache()
    {
        return roomDatabaseOperations.getArrayAPOD() ;
    }


    public void requestNILcache(RoomDatabase database)
    {
        roomDatabaseOperations.searchCacheNIL(database);
    }
    public void requestAPODcache(RoomDatabase database)
    {
        roomDatabaseOperations.searchCacheAPOD(database);
    }

    public void checkAPODItem(String date, RoomDatabase database)
    {
         roomDatabaseOperations.checkAPODItem(date,database);

    }
    public LiveData<SingleApodResponse> getAPODItem()
    {
        return roomDatabaseOperations.getItemAPOD();
    }
    public LiveData<ItemOffline> getNILItem()
    {
        return roomDatabaseOperations.getItemNIL();
    }

    public void checkNILItem(String nasa_id,RoomDatabase database) {
         roomDatabaseOperations.checkNILItem(nasa_id,database);

    }
}
