package com.example.nasaspacesight.ViewModels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;
import com.example.nasaspacesight.Room.RoomDatabase;

import java.util.List;

public class OfflineViewModelApod extends ViewModel {
    MainImageSearchRepo repo;

    public OfflineViewModelApod() {
        repo = MainImageSearchRepo.getInstance();
        //Items=new MutableLiveData<>();
    }

    public LiveData<DataWrapper<List<SingleApodResponse>>> getAPODcache()
    {
        return repo.getAPODcache();
    }


    public void requestAPODcache(RoomDatabase database)
    {
        repo.requestAPODcache(database);
    }
}
