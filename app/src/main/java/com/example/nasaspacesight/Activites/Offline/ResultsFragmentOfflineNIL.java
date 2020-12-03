package com.example.nasaspacesight.Activites.Offline;



import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.nasaspacesight.Activites.NIL.ResultsFragmentNIL;
import com.example.nasaspacesight.Activites.NIL.SearchDialogNIL;
import com.example.nasaspacesight.Activites.ParentFragment;
import com.example.nasaspacesight.Adapters.NormalImageRecycleAdapterNIL;
import com.example.nasaspacesight.Adapters.PagesReyclerAdapterNIL;
import com.example.nasaspacesight.POJO_NIL.Collection;
import com.example.nasaspacesight.R;
import com.example.nasaspacesight.Room.DatabaseInfo;
import com.example.nasaspacesight.Room.RoomDatabase;
import com.example.nasaspacesight.ViewModels.DataWrapper;
import com.example.nasaspacesight.ViewModels.ItemListViewModelNIL;
import com.example.nasaspacesight.ViewModels.OfflineViewModelNIL;

import java.util.HashMap;
import java.util.Objects;


public class ResultsFragmentOfflineNIL extends ResultsFragmentNIL {



    OfflineViewModelNIL offlineViewModelNIL;
    RoomDatabase db;



    @Override
    public void onResume() {
        super.onResume();
        //specificInit();
        Toast.makeText(getContext(), "RESUMEDEEE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void specificInit() {
        initDB();
        initCaching();
        dataLoaded();
    }
    private void initDB() {
        db= Room.databaseBuilder(Objects.requireNonNull(getContext()), RoomDatabase.class, DatabaseInfo.DB_NAME).build();
    }



    @Override
    public void search(HashMap<String, Object> query) {

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
    public void initSearchDialog() {
    }

    @Override
    public void initMenuOptions() {

    }



}
