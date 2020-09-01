package com.example.nasaspacesight.Activites;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nasaspacesight.R;
import com.example.nasaspacesight.ViewModels.ItemListViewModel;

import java.util.HashMap;

public class MainActivity extends ThemedActivity implements FragmentsExchanger {


    private Fragment resultFragment;
    private Fragment searchFragment;
    FragmentManager fragmentManager;
    ImageButton searchButton;
    Toolbar mainToolbar;
    FrameLayout frameLayout;
    ItemListViewModel viewModel;
    PlaceHolderFragment placeholder;

    TextView frameIndicatorTextView;
    private static final String TOOLBAR_TITLE="NASA Images";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    void switchToSearchFragment()
    {
        fragmentManager.beginTransaction().replace(R.id.main_fragment,searchFragment).addToBackStack(null).commit();
        setFrameIndicator("Search For Images");
    }

    void switchToResultFragment()
    {
        fragmentManager.beginTransaction().replace(R.id.main_fragment,resultFragment).addToBackStack(null).commit();
        setFrameIndicator("Search Results");

    }

    @Override
    public void onBackPressed()
    {
        this.finish();
    }

    private void BindViews()
    {
        LinearLayout linearLayout=findViewById(R.id.test);
        mainToolbar=findViewById(R.id.toolbar);
        searchButton =findViewById(R.id.main_search_button);
        frameIndicatorTextView=findViewById(R.id.frame_indicator);
        frameLayout=findViewById(R.id.main_fragment);
    }

    @Override
    public void init()
    {
        BindViews();
        initFragments();
        initSearchButtonClickListener();
        initToolBar();
        initViewModel();
        initPlaceHolder();
        //switchToSearchFragment();////////////////////testing
    }

    private void initPlaceHolder()
    {
        fragmentManager.beginTransaction().replace(R.id.main_fragment,placeholder).addToBackStack(null).commit();

    }


    private void initViewModel()
    {
        viewModel= new ViewModelProvider(this).get(ItemListViewModel.class);
    }

    private void initToolBar()
    {
        setSupportActionBar(mainToolbar);

        Window window=this.getWindow();

        //Change The Color of the Status Bar contents (battery,clock,etc..)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //Change The Color of the Status Bar Background
        window.setStatusBarColor(getColor(R.color.AcceptBlue));


    }

    private void initFragments()
    {
         placeholder = new PlaceHolderFragment();
        resultFragment=new ResultsFragment();
        searchFragment=new SearchFragment();
        fragmentManager=getSupportFragmentManager();
    }

    void initSearchButtonClickListener()
    {
        if(searchButton!=null) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchToSearchFragment();
                }
            };
            searchButton.setOnClickListener(listener);
        }
    }
    @Override
    public void exchangeFragments(String FragmentTag)
    {
        {switchToResultFragment();}
    }

    @Override
    public void search(HashMap<String,Object> query)
    {
        ResultsFragment searchable=(ResultsFragment)resultFragment;
        searchable.search(query);
    }
    public void setFrameIndicator(String text)
    {
        frameIndicatorTextView.setVisibility(View.VISIBLE);
        this.frameIndicatorTextView.setText(text);
    }

}
