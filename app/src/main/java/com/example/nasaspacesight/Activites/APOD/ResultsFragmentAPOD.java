package com.example.nasaspacesight.Activites.APOD;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.nasaspacesight.Activites.MainActivity;
import com.example.nasaspacesight.Activites.NIL.ImageDetailsActivityNIL;
import com.example.nasaspacesight.Activites.NIL.ResultsFragmentNIL;
import com.example.nasaspacesight.Activites.ParentFragment;
import com.example.nasaspacesight.Adapters.NormalImageRecyclerAdapterAPOD;
import com.example.nasaspacesight.ApiData.APOD.ApodClientAPI;
import com.example.nasaspacesight.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.R;
import com.example.nasaspacesight.ViewModels.DataWrapper;
import com.example.nasaspacesight.ViewModels.ItemListViewModelAPOD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.nasaspacesight.ApiData.APOD.ApodClientAPI.APOD_API_KEY;
import static com.example.nasaspacesight.ApiData.APOD.ApodClientAPI.APOD_API_KEY_VALUE;
import static com.example.nasaspacesight.ViewModels.DataWrapperStatus.FAILED;
import static com.example.nasaspacesight.ViewModels.DataWrapperStatus.LOADING;
import static com.example.nasaspacesight.ViewModels.DataWrapperStatus.SUCCESSED;

/**
 * A simple {@link Fragment} subclass.



 */

public class ResultsFragmentAPOD extends ParentFragment {

    ItemListViewModelAPOD viewModel;
    SearchDialogAPOD searchDialogAPOD;
    private static final String TAG = "ResultsFragmentAPOD";
    NormalImageRecyclerAdapterAPOD adapter;









    @Override
    public void onImageClick(int postion)
    {
        SingleApodResponse singleApodResponse = adapter.getItem(postion);
        Intent intent = new Intent(getContext(), ImageDetailsActivityNIL.class);
        intent.putExtra(ImageDetailsActivityNIL.TAG, ImageDetailsActivityNIL.INTENT_APOD);
        intent.putExtra(ImageDetailsActivityNIL.INTENT_APOD, (Serializable) singleApodResponse);
        startActivity(intent);

    }

    @Override
    public void specificInit() {
        dataLoaded();
       // searchWithTodayDate();
        searchDialogAPOD.show();
    }

    private void searchWithTodayDate()
    {
        HashMap<String,Object> searchMap=new HashMap<String, Object>() ;
        searchMap.put(APOD_API_KEY,APOD_API_KEY_VALUE);
        search(searchMap);

    }




    @Override
    public void initViewModel() {
        viewModel=new ViewModelProvider(this).get(ItemListViewModelAPOD.class);
    }

    @Override
    public void subscribeToViewModel() {
        viewModel.getItems().observe(getViewLifecycleOwner(), (Observer<DataWrapper>) this::updateViews);
    }


    @Override
    public void updateViews(DataWrapper items_) {

        try {
            DataWrapper<List<SingleApodResponse>> items= (DataWrapper<List<SingleApodResponse>>)items_;
            switch (items.getStatus()) {
                case LOADING:
                    dataLoading();
                    break;
                case FAILED:
                    dataFailed(items.getMessage());
                    break;
                case SUCCESSED:
                    dataLoaded();
                    List<SingleApodResponse> reversedList=new ArrayList<SingleApodResponse>();
                    for(int i=items.getCollection().size()-1;i>=0;i--)
                    {
                        reversedList.add(items.getCollection().get(i));
                    }
                    updateAdapter(reversedList);
                    break;

            }
        } catch (Exception e) {
            dataFailed("Service or Network Error");
        }

    }

    private void updateAdapter(List<SingleApodResponse> collection) {
        if (collection != null) {
            adapter.setImages(collection);
        }
    }

    @Override
    public void initRecyclerAdapter() {
        adapter=new NormalImageRecyclerAdapterAPOD(getContext(),this);
    }

    @Override
    public void initRecyclerView() {

        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultsRecyclerView.setAdapter(adapter);
        recyclerViewLayoutManager = (LinearLayoutManager) (resultsRecyclerView.getLayoutManager());


        resultsRecyclerView.setAdapter(adapter);
    }



    @Override
    public void initSearchDialog() {
         searchDialogAPOD=new SearchDialogAPOD( this.getContext(),this);
        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        lp.copyFrom(searchDialogAPOD.getWindow().getAttributes());
        lp.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width=ViewGroup.LayoutParams.MATCH_PARENT;
        searchDialogAPOD.getWindow().setAttributes(lp);

    }

    @Override
    public void initMenuOptions() {
        ((MainActivity)getActivity()).getMainToolbar().setOnMenuItemClickListener(item -> {
            switch (item.getItemId())
            {
                case R.id.search:
                    searchDialogAPOD.show();
                    break;
            }
            return false;
        });
    }



    @Override
    public void search(HashMap<String, Object> query) {
        try {
            dataLoading();
            lastQuery = query;
            viewModel.searchForImages(query);
        } catch (Exception e) {
            dataFailed("Service or Network Error");
        }
    }


}
