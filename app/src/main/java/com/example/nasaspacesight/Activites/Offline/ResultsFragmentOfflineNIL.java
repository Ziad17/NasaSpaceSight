package com.example.nasaspacesight.Activites.Offline;


import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.nasaspacesight.Activites.NIL.ResultsFragmentNIL;
import com.example.nasaspacesight.POJO_NIL.Collection;
import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.Room.DatabaseInfo;
import com.example.nasaspacesight.Room.RoomDatabase;
import com.example.nasaspacesight.ViewModels.DataWrapper;
import com.example.nasaspacesight.ViewModels.OfflineViewModelNIL;

import java.util.List;

public class ResultsFragmentOfflineNIL extends ResultsFragmentNIL {

    OfflineViewModelNIL offlineViewModelNIL;
    RoomDatabase db;




    @Override
    protected void dataLoading() {

    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void specificInit() {
        initDB();
        initCaching();
        dataLoaded();
    }
    private void initDB() {
        db= Room.databaseBuilder(getContext(), RoomDatabase.class, DatabaseInfo.DB_NAME).build();
    }

    private void initCaching()
    {
        offlineViewModelNIL.requestNILcache(db);
    }

    @Override
    public void initViewModel() {
        offlineViewModelNIL =new ViewModelProvider(this).get(OfflineViewModelNIL.class);
    }

    private static final String TAG = "ResultsFragmentOfflineN";
    @Override
    public void subscribeToViewModel() {
        offlineViewModelNIL.getNILcache().observe(getViewLifecycleOwner(), new Observer<DataWrapper<Collection>>() {
            @Override
            public void onChanged(DataWrapper<Collection> collectionDataWrapper) {
                updateViews(collectionDataWrapper);
            }
        });
    }

    @Override
    protected void updatePageAdapter(int i) {

    }


    @Override
    public void initSearchDialog() {
    }

    @Override
    public void initMenuOptions() {

    }
}
