package com.example.nasaspacesight.Activites;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nasaspacesight.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceHolderFragment extends Fragment implements ContextWithInitiativeBehavior{


    Button searchButton;
    public PlaceHolderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_holder, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }



    private void initSearchListener()
    {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity=(MainActivity)getContext();
                mainActivity.searchButton.callOnClick();
            }
        });
    }

    @Override
    public void init() {
        BindView();
        initSearchListener();

    }

    private void BindView() {
        searchButton=getView().findViewById(R.id.main_search_button_1);
    }
}
