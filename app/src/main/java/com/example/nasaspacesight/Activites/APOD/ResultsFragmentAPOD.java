package com.example.nasaspacesight.Activites.APOD;


import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.nasaspacesight.Activites.MainActivity;
import com.example.nasaspacesight.Activites.NIL.ImageDetailsActivityNIL;
import com.example.nasaspacesight.Activites.ParentFragment;
import com.example.nasaspacesight.Adapters.NormalImageRecyclerAdapterAPOD;
import com.example.nasaspacesight.PojoModels.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.PojoModels.Quiries.QueryAPOD;
import com.example.nasaspacesight.R;
import com.example.nasaspacesight.Room.RoomDatabase;
import com.example.nasaspacesight.PojoModels.DataWrapper;
import com.example.nasaspacesight.ViewModels.ItemListViewModelAPOD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.nasaspacesight.ApiData.APOD.ApodClientAPI.APOD_API_KEY;
import static com.example.nasaspacesight.ApiData.APOD.ApodClientAPI.APOD_API_KEY_VALUE;
import static com.example.nasaspacesight.Room.DatabaseInfo.DB_NAME;
import static com.example.nasaspacesight.PojoModels.DataWrapperStatus.FAILED;
import static com.example.nasaspacesight.PojoModels.DataWrapperStatus.LOADING;
import static com.example.nasaspacesight.PojoModels.DataWrapperStatus.SUCCESSED;

/**
 * A simple {@link Fragment} subclass.



 */

public class ResultsFragmentAPOD extends ParentFragment implements QueryHistoryOperationsAPOD, MainActivity.IonBackPressed {

    ItemListViewModelAPOD viewModel;
    SearchDialogAPOD searchDialogAPOD;
    private static final String TAG = "ResultsFragmentAPOD";
    NormalImageRecyclerAdapterAPOD adapter;
    protected HistoryBottomSheetDialogAPOD historyBottomSheetDialog;

    RoomDatabase db;



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
        searchWithTodayDate();
        intiDb();
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
        viewModel.getHistoryAPOD().observe(getViewLifecycleOwner(), listDataWrapper -> updateHistory(listDataWrapper.getCollection()));

    }

    private void updateHistory(List<QueryAPOD> collection)
    {
        historyBottomSheetDialog.updateList(collection);

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

    @Override
    public void initHistoryDialog() {
        historyBottomSheetDialog = new HistoryBottomSheetDialogAPOD(null,this,getContext(), R.style.BottomSheetDialogTheme);

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
                case R.id.history:
                    viewModel.searchHistoryAPOD(db);
                    historyBottomSheetDialog.show();
                    break;
            }
            return false;
        });
    }


    private void intiDb() {
        db= Room.databaseBuilder(getContext(), RoomDatabase.class,DB_NAME).fallbackToDestructiveMigration().build();
    }

    @Override
    public void search(HashMap<String, Object> query) {
        try {
            dataLoading();
            lastQuery.add(query);
            viewModel.insertHistoryAPOD(new QueryAPOD(lastQuery.peek()), db);

            viewModel.searchForImages(query);
        } catch (Exception e) {
            dataFailed("Service or Network Error");
        }
    }


    @Override
    public void onHistoryDeleted(QueryAPOD queryAPOD)

    {

        viewModel.deleteHistoryAPOD(queryAPOD,db);
        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHistoryClicked(QueryAPOD queryAPOD) {
        Toast.makeText(getContext(), queryAPOD.toString(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onHistorySearch(QueryAPOD queryNIL) {
        historyBottomSheetDialog.hide();


        dataLoading();
        viewModel.searchForImages(queryNIL.toMap());

    }


    @Override
    public boolean onBackPressedI() {
        try {
            if(lastQuery.size()==1)
            {return true;}
            lastQuery.pop();
            dataLoading();
            viewModel.searchForImages(lastQuery.peek());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
