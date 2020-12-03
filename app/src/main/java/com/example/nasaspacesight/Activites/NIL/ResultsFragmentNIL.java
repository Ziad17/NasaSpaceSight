package com.example.nasaspacesight.Activites.NIL;


import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.nasaspacesight.Activites.MainActivity;
import com.example.nasaspacesight.Activites.ParentFragment;
import com.example.nasaspacesight.Adapters.NormalImageRecycleAdapterNIL;
import com.example.nasaspacesight.Adapters.PagesReyclerAdapterNIL;
import com.example.nasaspacesight.ApiData.Images.ImagesClientAPI;
import com.example.nasaspacesight.POJO_NIL.Collection;
import com.example.nasaspacesight.POJO_NIL.Item;
import com.example.nasaspacesight.ViewModels.DataWrapper;
import com.example.nasaspacesight.R;
import com.example.nasaspacesight.ViewModels.ItemListViewModelNIL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.nasaspacesight.ViewModels.DataWrapperStatus.FAILED;
import static com.example.nasaspacesight.ViewModels.DataWrapperStatus.LOADING;
import static com.example.nasaspacesight.ViewModels.DataWrapperStatus.SUCCESSED;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragmentNIL extends ParentFragment implements PagesReyclerAdapterNIL.OnPageNumberClickListner {


    private static final String TAG = "ResultsFragmentNIL";
    protected NormalImageRecycleAdapterNIL adapter;
    protected SearchDialogNIL searchDialogNIL;
    protected ItemListViewModelNIL viewModel;
    protected PagesReyclerAdapterNIL pagesReyclerAdapterNIL;
    protected RecyclerView pageRecyclerView;


    @Override
    public void specificInit() {
        dataLoaded();
        searchDialogNIL.show();
        /*HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put(ImagesClientAPI.NIL_QUERY, "galaxy");
        search(hashMap);
*/
    }

    @Override
    public void initMenuOptions() {
        ((MainActivity) getActivity()).getMainToolbar().setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.search:
                    searchDialogNIL.show();
                    break;
            }
            return false;
        });
    }


    @Override
    public void initSearchDialog() {
        searchDialogNIL = new SearchDialogNIL(this.getContext(), this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(searchDialogNIL.getWindow().getAttributes());
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        searchDialogNIL.getWindow().setAttributes(lp);
    }

    @Override
    public void initRecyclerAdapter() {
        adapter = new NormalImageRecycleAdapterNIL(getContext(), this);
        pagesReyclerAdapterNIL = new PagesReyclerAdapterNIL(getContext(), this);
    }

    @Override
    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        if (pageRecyclerView != null) {
            pageRecyclerView.setLayoutManager(linearLayoutManager);
            pageRecyclerView.setAdapter(pagesReyclerAdapterNIL);
            pageRecyclerView.bringToFront();
        }
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultsRecyclerView.setAdapter(adapter);
        recyclerViewLayoutManager = (LinearLayoutManager) (resultsRecyclerView.getLayoutManager());
    }

    @Override
    public void BindView() {
        super.BindView();
        pageRecyclerView = getView().findViewById(R.id.page_view);
    }

    @Override
    public void search(HashMap<String, Object> query) {
        try {
            dataLoading();
            lastQuery = query;
            viewModel.SearchForImages(lastQuery);
        } catch (Exception e) {
            dataFailed("Service or Network Error");
        }

    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ItemListViewModelNIL.class);
    }

    @Override
    public void subscribeToViewModel() {
        viewModel.getItems().observe(getViewLifecycleOwner(), (Observer<DataWrapper>) dataWrapper -> updateViews(dataWrapper));
    }

    @Override
    public void updateViews(DataWrapper items_) {

        try {
            DataWrapper<Collection> items = (DataWrapper<Collection>) items_;
            switch (items.getStatus()) {
                case LOADING:
                    dataLoading();
                    break;
                case FAILED:
                    dataFailed(items_.getMessage());
                    break;
                case SUCCESSED:
                    dataLoaded();
                    updateAdapter(items.getCollection());
                    //updatePageAdapter(items.getCollection().getMetadata().getTotalHits() / 100 > 100 ? 99 : items.getCollection().getMetadata().getTotalHits() / 100);
                    //updateTotalHitsTextView(items.getCollection().getMetadata().getTotalHits());
                    break;

            }
        } catch (Exception e) {
            dataFailed("Service Or Network Error");
            Log.e(TAG, "updateViews: ", e);
        }


    }


    protected void updatePageAdapter(int i) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int j = 1; j <= i; j++) {
            numbers.add(j);
        }
        int current_page = Integer.valueOf(String.valueOf(lastQuery.get(ImagesClientAPI.NIL_PAGE_NUMBER)));
        pagesReyclerAdapterNIL.setPages(numbers, current_page);
    }


    //TODO: Optimise Those Buttons
    public void updateAdapter(Collection items) {
        if (items != null) {
            adapter.setArrayOfItems(items.getItems());
        }

    }


    @Override
    public void onImageClick(int postion) {
        Intent intent = new Intent(getContext(), ImageDetailsActivityNIL.class);
        Item item = adapter.getItem(postion);
        intent.putExtra(ImageDetailsActivityNIL.TAG, ImageDetailsActivityNIL.INTENT_NIL);
        intent.putExtra(ImageDetailsActivityNIL.INTENT_NIL, (Serializable) item);
        startActivity(intent);
    }


    @Override
    public void OnPageClick(int postion) {
        try {
            lastQuery.put(ImagesClientAPI.NIL_PAGE_NUMBER, pagesReyclerAdapterNIL.getPages().get(postion));
            search(lastQuery);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Can't Retrieve this page", Toast.LENGTH_LONG).show();
        }
    }


}


