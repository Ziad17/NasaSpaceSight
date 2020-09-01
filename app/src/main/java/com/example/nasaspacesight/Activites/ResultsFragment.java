package com.example.nasaspacesight.Activites;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nasaspacesight.Adapters.NormalImageRecycleAdapter;
import com.example.nasaspacesight.Adapters.PagesReyclerAdapter;
import com.example.nasaspacesight.POJO.Collection;
import com.example.nasaspacesight.POJO.Item;
import com.example.nasaspacesight.R;
import com.example.nasaspacesight.ViewModels.ItemListViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment implements ContextWithInitiativeBehavior, NormalImageRecycleAdapter.OnImageClickLisetenr , PagesReyclerAdapter.OnPageNumberClickListner {


    private HashMap<String,Object> lastQuery;
    private static final String TAG = "ResultsFragment";
    RecyclerView resultsRecyclerView;
    TextView totalHitsTextView;
    NormalImageRecycleAdapter adapter;
    PagesReyclerAdapter pagesReyclerAdapter;
    ProgressBar mainProgressBar;
    LinearLayout mainLinearLayout;
    RecyclerView pageRecyclerView;
    LinearLayout failLinearLayout;
    ImageButton reloadButton;

    ListView listView;
    private ItemListViewModel viewModel;
    public ResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void init()
    {
        BindView();
        initViewModel();
        subscribeToViewModel();
        initRecyclerAdapter();
        initRecyclerView();
        search(lastQuery);
        initReloadButtonListener();
    }


    private static int CONUTER=1;

    public void search(HashMap<String,Object> query_)
    {

        if(lastQuery==null)
        {
            lastQuery = query_;
        }
        else {
            lastQuery = query_;
            try {
                dataLoading();

                if (!(boolean) lastQuery.get("DATE_SEARCH_ENABLED")) {
                    String query = String.valueOf(lastQuery.get("query"));
                    String description = String.valueOf(lastQuery.get("description"));
                    String location = String.valueOf(lastQuery.get("location"));
                    String title = String.valueOf(lastQuery.get("title"));
                    String photographer = String.valueOf(lastQuery.get("photographer"));
                    String keywords = String.valueOf(lastQuery.get("keywords"));
                    Integer pageNumber = Integer.valueOf(String.valueOf(lastQuery.get("pageNumber")));


                    viewModel.SearchForImages(query, keywords, description, location, "", photographer, title, pageNumber);
                } else {
                    String query = String.valueOf(lastQuery.get("query"));
                    String description = String.valueOf(lastQuery.get("description"));
                    String location = String.valueOf(lastQuery.get("location"));
                    String title = String.valueOf(lastQuery.get("title"));
                    String photographer = String.valueOf(lastQuery.get("photographer"));
                    String keywords = String.valueOf(lastQuery.get("keywords"));
                    Integer endYear = Integer.valueOf(String.valueOf(lastQuery.get("endYear")));
                    Integer startYear = Integer.valueOf(String.valueOf(lastQuery.get("startYear")));
                    Integer pageNumber = Integer.valueOf(String.valueOf(lastQuery.get("pageNumber")));


                    viewModel.SearchForImages(query, keywords, description, location, "", photographer, title, startYear, endYear, pageNumber);

                }
            } catch (Exception e) {
                dataFailed();
            }
        }


    }

    private void hideAllViews()
    {
        mainProgressBar.setVisibility(View.GONE);
        mainLinearLayout.setVisibility(View.GONE);
        failLinearLayout.setVisibility(View.GONE);

    }

    void initReloadButtonListener()
    {

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(lastQuery);
            }
        };
        if(reloadButton!=null)
        {
            reloadButton.setOnClickListener(listener);

        }
    }

    private void initRecyclerAdapter()
    {
        adapter=new NormalImageRecycleAdapter(getContext(),this);
        pagesReyclerAdapter=new PagesReyclerAdapter(getContext(),this);

    }

    private void initRecyclerView()
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        pageRecyclerView.setLayoutManager(linearLayoutManager);
        pageRecyclerView.setAdapter(pagesReyclerAdapter);
        pageRecyclerView.bringToFront();

        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultsRecyclerView.setAdapter(adapter);
    }

    private void BindView() {
        pageRecyclerView=getView().findViewById(R.id.page_view);
        totalHitsTextView=getView().findViewById(R.id.search_total_hits);
        resultsRecyclerView=getView().findViewById(R.id.search_recycleView);
        mainLinearLayout=getView().findViewById(R.id.results_layout);
        mainProgressBar=getView().findViewById(R.id.loading_layout);
        failLinearLayout=getView().findViewById(R.id.offline_layout);
        reloadButton=getView().findViewById(R.id.reload_results_button);
       // listView=getView().findViewById(R.id.pages_listview);

    }



    private void initViewModel()
    {
        viewModel=new ViewModelProvider(this).get(ItemListViewModel.class);
    }


    private void subscribeToViewModel()
    {
        viewModel.getItems().observe(this, new Observer<Collection>() {
            @Override
            public void onChanged(Collection items) {
                if(items!=null)
                {
                    dataLoaded();
                    updateViews(items);
                }
                else
                    {
                        dataFailed();
                }
            }
        });
    }

    private void updateViews(Collection items)
    {
        updateAdapter(items);
        updatePageAdapter(items.getMetadata().getTotalHits()/100>100 ? 99 : items.getMetadata().getTotalHits()/100);
        updateTotalHitsTextView(items.getMetadata().getTotalHits());

    }

    private void updatePageAdapter(int i)
    {
        ArrayList<Integer> numbers=new ArrayList<>();
        for(int j=1;j<=i;j++)
        {
            numbers.add(j);

        }
        int current_page=Integer.valueOf(String.valueOf(lastQuery.get("pageNumber")));
        pagesReyclerAdapter.setPages(numbers,current_page);
    }
    private void updateTotalHitsTextView(int size)
    {
        totalHitsTextView.setText(String.valueOf(size)+" Total Matches");
    }

    private void updateAdapter(Collection items)
    {
        adapter.setArrayOfItems(items.getItems());

    }
    @Override
    public void onImageClick(int postion)
    {
        Intent intent=new Intent(getContext(),ImageDetailsActivity.class);
        Item item=adapter.getItem(postion);
        intent.putExtra(ImageDetailsActivity.INTENT_ITEM,(Serializable) item);
        Log.e(TAG, "onImageClick: "+item.toString());
        startActivity(intent);
    }
    private void initHorizontalListView(int number_of_pages)
    {
        ArrayList<Integer> pages=new ArrayList<>();
        for(int i=1;i<=number_of_pages;i++)
        {
            pages.add(i);
        }
        listView.setAdapter(new ArrayAdapter<Integer>(getContext(),R.layout.simple_list_page_item,pages));
    }

    public void dataLoading()
    {
        displayLoading(true);
        displayFaliure(false);
        displaySuccess(false);
        mainProgressBar.bringToFront();


    }

    public void dataLoaded()
    {

        displayLoading(false);
        displayFaliure(false);
        displaySuccess(true);
        mainLinearLayout.bringToFront();

    }

    public void dataFailed()
    {

        failLinearLayout.bringToFront();
        displayLoading(false);
        displayFaliure(true);
        displaySuccess(false);
    }
    void displayLoading(boolean isLoading)
    {
        mainProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    void displaySuccess(boolean isLoaded)
    {
        mainLinearLayout.setVisibility(isLoaded ? View.VISIBLE : View.GONE);
    }

    void displayFaliure(boolean isFailed)
    {
        failLinearLayout.setVisibility(isFailed ? View.VISIBLE : View.GONE);
    }


    @Override
    public void OnPageClick(int postion) {
        if(lastQuery!=null)
        {

            if (lastQuery.containsKey("pageNumber"))
            {
                lastQuery.put("pageNumber",postion+1);

            }
            search(lastQuery);
        }
    }
}


