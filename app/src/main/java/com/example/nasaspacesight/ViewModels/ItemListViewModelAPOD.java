package com.example.nasaspacesight.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.PojoModels.DataWrapper;
import com.example.nasaspacesight.PojoModels.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.PojoModels.Quiries.QueryAPOD;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;
import com.example.nasaspacesight.Room.RoomDatabase;

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

    public void insertHistoryAPOD(QueryAPOD queryAPOD, RoomDatabase roomDatabase)
    {
        repo.insertHistoryAPOD(queryAPOD,roomDatabase);
    }

    public void deleteHistoryAPOD(QueryAPOD queryAPOD,RoomDatabase roomDatabase)
    {
        repo.deleteHistoryAPOD(queryAPOD,roomDatabase);
    }
    public void deleteAllHistoryAPOD(RoomDatabase roomDatabase)
    {
        repo.deleteAllHistoryAPOD(roomDatabase);
    }

    public LiveData<DataWrapper<List<QueryAPOD>>> getHistoryAPOD()
    {
        return repo.getHistoryAPOD();
    }

    public void searchHistoryAPOD(RoomDatabase roomDatabase)
    {
        repo.searchHistoryAPOD(roomDatabase);
    }



}
