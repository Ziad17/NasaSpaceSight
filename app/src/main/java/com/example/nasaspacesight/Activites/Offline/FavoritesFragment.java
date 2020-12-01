package com.example.nasaspacesight.Activites.Offline;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nasaspacesight.Activites.ContextWithInitiativeBehavior;
import com.example.nasaspacesight.Adapters.FavoritesAdapter;
import com.example.nasaspacesight.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import static com.example.nasaspacesight.Activites.NIL.ImageDetailsActivityNIL.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements ContextWithInitiativeBehavior {

    ViewPager viewPager;
    TabLayout tabLayout;
    TabItem imagesTabItem;
    TabItem apodTabItem;
    ResultsFragmentOfflineResultsAPOD fragmentOfflineAPOD;
    ResultsFragmentOfflineNIL fragmentOfflineNIL;
    FavoritesAdapter favoritesAdapter;
    public FavoritesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);

    }

    @Override
    public void init()
    {
        BindViews();
        initFragments();
        initTabLayout();

    }

    private void initTabLayout()
    {
        viewPager.setAdapter(favoritesAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Log.e(TAG, "initTabLayout: " );
        TabLayout.Tab images= tabLayout.getTabAt(0);
        images.setIcon(favoritesAdapter.getIcons().get(1));

        TabLayout.Tab apod= tabLayout.getTabAt(1);
        apod.setIcon(favoritesAdapter.getIcons().get(0));
}

    private void initFragments() {
        fragmentOfflineNIL =new ResultsFragmentOfflineNIL();
        fragmentOfflineAPOD =new ResultsFragmentOfflineResultsAPOD();
        favoritesAdapter=new FavoritesAdapter(getParentFragmentManager(),0);
        favoritesAdapter.addFragment(fragmentOfflineNIL,getContext().getDrawable(R.drawable.ic_today_black_24dp));
        favoritesAdapter.addFragment(fragmentOfflineAPOD,getContext().getDrawable(R.drawable.ic_image_black_24dp));


    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void BindViews()
    {
        viewPager=getView().findViewById(R.id.view_pager);
        tabLayout=getView().findViewById(R.id.main_tablayout);
        apodTabItem=getView().findViewById(R.id.nil_offline);
        imagesTabItem=getView().findViewById(R.id.apod_offline);
    }

}
