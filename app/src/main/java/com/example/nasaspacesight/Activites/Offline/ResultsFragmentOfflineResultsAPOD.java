package com.example.nasaspacesight.Activites.Offline;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.nasaspacesight.Activites.APOD.ResultsFragmentAPOD;
import com.example.nasaspacesight.Room.DatabaseInfo;
import com.example.nasaspacesight.Room.RoomDatabase;
import com.example.nasaspacesight.ViewModels.OfflineViewModelApod;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragmentOfflineResultsAPOD extends ResultsFragmentAPOD {

    private static final String TAG = "ResultsFragmentOfflineA";
    OfflineViewModelApod offlineViewModelApod;

    RoomDatabase db;
    @Override
    public void init() {
        super.init();
        initDB();
        initCaching();
        dataLoaded();




    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void specificInit() {

    }

    private void initDB() {
        db= Room.databaseBuilder(getContext(),RoomDatabase.class, DatabaseInfo.DB_NAME).build();

    }

    private void initCaching() {
        offlineViewModelApod.requestAPODcache(db);
    }

    @Override
    public void initViewModel() {
        offlineViewModelApod=new ViewModelProvider(this).get(OfflineViewModelApod.class);
    }

    @Override
    public void subscribeToViewModel() {
        offlineViewModelApod.getAPODcache().observe(getViewLifecycleOwner(), this::updateViews);
    }


    @Override
    public void initSearchDialog() {
    }

    @Override
    public void initMenuOptions() {

    }
}
