package com.example.nasaspacesight.Activites.Offline;


import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.nasaspacesight.Activites.NIL.ResultsFragmentNIL;
import com.example.nasaspacesight.Room.DatabaseInfo;
import com.example.nasaspacesight.Room.RoomDatabase;
import com.example.nasaspacesight.ViewModels.OfflineViewModelNIL;

/**
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragmentOfflineNIL extends ResultsFragmentNIL {

    OfflineViewModelNIL offlineViewModel;
RoomDatabase db;
    @Override
    public void init() {
        super.init();
        initDB();
        initCaching();
        dataLoaded();
    }

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

    }
    private void initDB() {
        db= Room.databaseBuilder(getContext(), RoomDatabase.class, DatabaseInfo.DB_NAME).build();

    }

    private void initCaching()
    {
        offlineViewModel.requestNILcache(db);
    }

    @Override
    public void initViewModel() {
        offlineViewModel=new ViewModelProvider(this).get(OfflineViewModelNIL.class);
    }

    @Override
    public void subscribeToViewModel() {
        offlineViewModel.getNILcache().observe(getViewLifecycleOwner(), this::updateViews);
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
