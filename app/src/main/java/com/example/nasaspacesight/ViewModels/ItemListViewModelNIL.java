package com.example.nasaspacesight.ViewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.PojoModels.DataWrapper;
import com.example.nasaspacesight.PojoModels.POJO_NIL.Collection;
import com.example.nasaspacesight.PojoModels.Quiries.QueryNIL;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;
import com.example.nasaspacesight.Room.RoomDatabase;

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



    public void insertHistoryNIL(QueryNIL queryNIL, RoomDatabase roomDatabase)
    {
        repo.insertHistoryNIL(queryNIL,roomDatabase);
    }

    public void deleteHistoryNIL(QueryNIL queryNIL,RoomDatabase roomDatabase)
    {
        repo.deleteHistoryNIL(queryNIL,roomDatabase);
    }
    public void deleteAllHistoryNIL(RoomDatabase roomDatabase)
    {
        repo.deleteAllHistoryNIL(roomDatabase);
    }

    public LiveData<DataWrapper<List<QueryNIL>>> getHistoryNIL()
    {
        return repo.getHistoryNIL();
    }

    public void searchHistoryNIL(RoomDatabase roomDatabase)
    {
        repo.searchHistoryNIL(roomDatabase);
    }




}

