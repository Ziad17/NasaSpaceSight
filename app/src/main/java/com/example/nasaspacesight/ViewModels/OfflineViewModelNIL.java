package com.example.nasaspacesight.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;
import com.example.nasaspacesight.Room.RoomDatabase;

import java.util.List;

public class OfflineViewModelNIL extends ViewModel {

    MainImageSearchRepo repo;

    public OfflineViewModelNIL() {
        repo = MainImageSearchRepo.getInstance();
        //Items=new MutableLiveData<>();
    }

    public LiveData<DataWrapper<List<Item>>> getNILcache()
    {
        return repo.getNILcache();
    }


    public void requestNILcache(RoomDatabase database)
    {
        repo.requestNILcache(database);
    }
}
