package com.example.nasaspacesight.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nasaspacesight.PojoModels.DataWrapper;
import com.example.nasaspacesight.PojoModels.POJO_NIL.Collection;
import com.example.nasaspacesight.Repository.MainImageSearchRepo;
import com.example.nasaspacesight.Room.RoomDatabase;

public class OfflineViewModelNIL extends ViewModel {

    MainImageSearchRepo repo;

    public OfflineViewModelNIL() {
        repo = MainImageSearchRepo.getInstance();
        //Items=new MutableLiveData<>();
    }

    public LiveData<DataWrapper<Collection>> getNILcache()
    {
        return repo.getNILcache();
    }


    public void requestNILcache(RoomDatabase database)
    {
        repo.requestNILcache(database);
    }
}
