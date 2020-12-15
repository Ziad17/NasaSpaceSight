package com.example.nasaspacesight.Activites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasaspacesight.Activites.NIL.SearchableFragment;
import com.example.nasaspacesight.Adapters.NormalImageRecycleAdapterNIL;
import com.example.nasaspacesight.R;
import com.example.nasaspacesight.PojoModels.DataWrapper;

import java.util.HashMap;
import java.util.Stack;

public abstract class ParentFragment extends Fragment implements SearchableFragment,ContextWithInitiativeBehavior, NormalImageRecycleAdapterNIL.OnImageClickLisetenr {

    protected Stack<HashMap<String, Object>> lastQuery;
    protected LinearLayout mainProgressBar;
    protected ScrollView mainLinearLayout;
    protected RecyclerView resultsRecyclerView;
    protected TextView totalHitsTextView;

    protected LinearLayoutManager recyclerViewLayoutManager;




    public ParentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public abstract void specificInit();
    public abstract void initMenuOptions();
    public abstract void initViewModel();
    public abstract void subscribeToViewModel();
    public abstract void initRecyclerAdapter();
    public abstract void initRecyclerView();
    public abstract void updateViews(DataWrapper dataWrapper);
    public abstract void initHistoryDialog();


    public abstract void initSearchDialog();

    public void BindView()
    {
        mainLinearLayout = getView().findViewById(R.id.results_layout);
        mainProgressBar = getView().findViewById(R.id.loading_layout);
        resultsRecyclerView = getView().findViewById(R.id.search_recycleView);
        totalHitsTextView = getView().findViewById(R.id.search_total_hits);
    }


    public abstract void search(HashMap<String, Object> query);
    @Override
    public void init() {
        BindView();
        initViewModel();
        subscribeToViewModel();
        initStack();
        initRecyclerAdapter();
        initRecyclerView();
        initSearchDialog();
        initMenuOptions();
        initHistoryDialog();
        specificInit();
    }

    protected  void initStack()
    {
        lastQuery=new Stack<>();
    }


    protected void updateTotalHitsTextView(int size) {
        totalHitsTextView.setText(String.valueOf(size) + " Total Matches");
    }

    protected void dataLoading() {
        Toast.makeText(getContext(), "Loading..", Toast.LENGTH_SHORT).show();
        displaySuccess(false);
        displayLoading(true);
    }

    protected void dataLoaded() {
        displayLoading(false);
        displaySuccess(true);


    }

    protected void dataFailed(String err_msg) {

        Toast.makeText(getContext(), err_msg, Toast.LENGTH_SHORT).show();
        dataLoaded();

    }
    protected void displayLoading(boolean isLoading) {
        mainProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    protected void displaySuccess(boolean isLoaded) {
        mainLinearLayout.setVisibility(isLoaded ? View.VISIBLE : View.GONE);
    }

}
