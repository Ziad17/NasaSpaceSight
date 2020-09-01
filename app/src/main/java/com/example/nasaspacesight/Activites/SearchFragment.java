package com.example.nasaspacesight.Activites;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.nasaspacesight.R;
import com.example.nasaspacesight.ViewModels.ItemListViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import static com.example.nasaspacesight.Util.Constants.SEARCH_START_YEAR;


/**
 * TODO: the specifications of the search are addressed here in this fragment.
 *
 * TODO: VERY IMPORTANT:::MOVE THE SEARCH LOGIC AND VIEW MODEL TO THE RESULTS FRAGMENT
 * Search queries like (name,year,photographerEditText,keywords)
 * <p>
 * <p>
 * The Verification of the user input is not important to check as the api controls it anyway
 * TO CHECK: i altered the frameLayout to scrollView
 */
public class SearchFragment extends Fragment implements ContextWithInitiativeBehavior {


    private static final int END_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    ArrayList<Integer> yearsArrayStart;
    FragmentsExchanger fragmentsExchanger;
    ArrayList<Integer> yearsArrayEnd;
    EditText queryEditText;
    EditText locationEditText;
    EditText photographerEditText;
    EditText descriptionEditText;
    EditText titleEditText;
    EditText keyWordsEditText;
    Button searchButton;
    Button resetButton;
    CheckBox unlockYearsCheckBox;
    Spinner startYearSpinner;
    Spinner endYearSpinner;
    LinearLayout yearSearchLinearLayout;
    Button exitButton;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static String getTAG() {

        return "SearchFragment";
    }

    private static final String TAG = "SearchFragment";







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TODO: check if the bind view method reaally gets called without exception

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();

    }

    void populateSpinners() {
        Log.d(TAG, "populateSpinners: ");
        for (int i = SEARCH_START_YEAR; i <= END_YEAR; i++) {
            yearsArrayStart.add(i);
        }
        Collections.reverse(yearsArrayStart);
        startYearSpinner.setAdapter(null);
        startYearSpinner.setAdapter(new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, yearsArrayStart));
        endYearSpinnerLimiter();
    }

    void endYearSpinnerLimiter() {
        Log.d(TAG, "endYearSpinnerLimiter: ");
        yearsArrayEnd=new ArrayList<>();
        int CURRENT_VALUE = (int) startYearSpinner.getSelectedItem();
        for (int i = CURRENT_VALUE; i <= END_YEAR; i++) {
            yearsArrayEnd.add(i);
        }
        Collections.reverse(yearsArrayEnd);
        endYearSpinner.setAdapter(null);
        endYearSpinner.setAdapter(new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_dropdown_item, yearsArrayEnd));
    }

    void initEndYearSpinnerListener() {

        Spinner.OnItemSelectedListener listener = new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endYearSpinnerLimiter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        startYearSpinner.setOnItemSelectedListener(listener);

    }



    @Override
    public void init() {

        BindView();
        lockLayout(false);
        populateSpinners();
        initEndYearSpinnerListener();
        initSearchButtonListener();
        initCheckBoxChangedListener();
        initResetButtonListener();
        initFragmentExchanger();
        initExitButtonClickListener();
        initEnterPressedListener(new ArrayList<EditText>(Arrays.asList(descriptionEditText,keyWordsEditText,locationEditText,photographerEditText,queryEditText,titleEditText)));

    }

    private void initFragmentExchanger()
    {
        fragmentsExchanger=(FragmentsExchanger)getContext();

    }

    private void initEnterPressedListener(ArrayList<EditText> arrayList)
    {
        View.OnKeyListener listener=new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER)
                {
                    searchButton.performClick();
                }
                return false;
            }
        };
        for (EditText editText:arrayList)
        {
            editText.setOnKeyListener(listener);
        }
    }

    private void BindView() {
        unlockYearsCheckBox = getView().findViewById(R.id.date_search);
        queryEditText = getView().findViewById(R.id.query);
        locationEditText = getView().findViewById(R.id.location);
        photographerEditText = getView().findViewById(R.id.photographer_name);
        descriptionEditText = getView().findViewById(R.id.description);
        titleEditText = getView().findViewById(R.id.title_search);
        keyWordsEditText = getView().findViewById(R.id.keywords);
        startYearSpinner=getView().findViewById(R.id.year_start);
        endYearSpinner=getView().findViewById(R.id.year_end);
        exitButton = getView().findViewById(R.id.exit_button);
        resetButton = getView().findViewById(R.id.reset_button);
        searchButton = getView().findViewById(R.id.button_search);
        yearSearchLinearLayout = getView().findViewById(R.id.date_search_layout);
        yearsArrayEnd=new ArrayList<Integer>();
        yearsArrayStart=new ArrayList<Integer>();
    }


    private void initExitButtonClickListener()
    {
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFragment();
            }
        };
        exitButton.setOnClickListener(listener);
    }

    private void hideFragment()
    {
        if (getContext() instanceof FragmentsExchanger)
        {
            fragmentsExchanger.exchangeFragments(getTAG());

        }
        else throw new RuntimeException("The activity must implement the Fragment Exchanger");
    }

    private void initResetButtonListener() {
        Button.OnClickListener listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetViews();

            }
        };
        resetButton.setOnClickListener(listener);
    }

    private void initSearchButtonListener() {
        Button.OnClickListener listener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchButtonClicked();
               // searchButtonClickedTesting();///////testing/////
            }
        };
        searchButton.setOnClickListener(listener);

    }
    void searchButtonClicked()
    {

        HashMap<String,Object> searchHashMap=new HashMap<String,Object>();
//THE API CALL AND INITLIZATION SHOULD BE DONE IN THE RESULTS FRAGMENT
        //TODO: Change the way the data passed to other fragments by caching the results in the DB
        boolean DATE_SEARCH_ENABLED = unlockYearsCheckBox.isChecked();
        String query = queryEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String title = titleEditText.getText().toString();
        String photographer = photographerEditText.getText().toString();
        String keywords = keyWordsEditText.getText().toString();
        if (!DATE_SEARCH_ENABLED) {
            searchHashMap.put("query",query);
            searchHashMap.put("description",description);
            searchHashMap.put("location",location);
            searchHashMap.put("title",title);
            searchHashMap.put("photographer",photographer);
            searchHashMap.put("keywords",keywords);
            searchHashMap.put("DATE_SEARCH_ENABLED",false);
            searchHashMap.put("pageNumber",1);

        } else {
            Integer endYear=Integer.valueOf(endYearSpinner.getSelectedItem().toString());
            Integer startYear=Integer.valueOf(startYearSpinner.getSelectedItem().toString());
            searchHashMap.put("query",query);
            searchHashMap.put("description",description);
            searchHashMap.put("location",location);
            searchHashMap.put("title",title);
            searchHashMap.put("photographer",photographer);
            searchHashMap.put("keywords",keywords);
            searchHashMap.put("DATE_SEARCH_ENABLED",true);
            searchHashMap.put("endYear",endYear);
            searchHashMap.put("startYear",startYear);
            searchHashMap.put("pageNumber",1);

        }
        hideFragment();
        fragmentsExchanger.search(searchHashMap);


    }

    void searchButtonClickedTesting()
    {

//THE API CALL AND INITLIZATION SHOULD BE DONE IN THE RESULTS FRAGMENT
        HashMap<String,Object> searchHashMap=new HashMap<String,Object>();

        //TODO: Change the way the data passed to other fragments by caching the results in the DB
        boolean DATE_SEARCH_ENABLED = unlockYearsCheckBox.isChecked();
        String query = "earth";
        String description = descriptionEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String title = titleEditText.getText().toString();
        String photographer = photographerEditText.getText().toString();
        String keywords = "";
        if (!DATE_SEARCH_ENABLED) {
            searchHashMap.put("query",query);
            searchHashMap.put("description",description);
            searchHashMap.put("location",location);
            searchHashMap.put("title",title);
            searchHashMap.put("photographer",photographer);
            searchHashMap.put("keywords",keywords);
            searchHashMap.put("DATE_SEARCH_ENABLED",false);
            searchHashMap.put("pageNumber",1);

        } else {
            Integer endYear=Integer.valueOf(endYearSpinner.getSelectedItem().toString());
            Integer startYear=Integer.valueOf(startYearSpinner.getSelectedItem().toString());
            searchHashMap.put("query",query);
            searchHashMap.put("description",description);
            searchHashMap.put("location",location);
            searchHashMap.put("title",title);
            searchHashMap.put("photographer",photographer);
            searchHashMap.put("keywords",keywords);
            searchHashMap.put("DATE_SEARCH_ENABLED",true);
            searchHashMap.put("endYear",endYear);
            searchHashMap.put("startYear",startYear);
            searchHashMap.put("pageNumber",1);

        }
        fragmentsExchanger.search(searchHashMap);
        hideFragment();

    }



    void resetViews()
    {
        queryEditText.setText("");
        locationEditText.setText("");
        photographerEditText.setText("");
        descriptionEditText.setText("");
        titleEditText.setText("");
        keyWordsEditText.setText("");
    }

    void initCheckBoxChangedListener() {

        CheckBox.OnCheckedChangeListener listener = new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lockLayout(isChecked);
            }
        };
        unlockYearsCheckBox.setChecked(false);
        unlockYearsCheckBox.setOnCheckedChangeListener(listener);

    }

    void lockLayout(boolean VALUE) {
        if (VALUE) {
            yearSearchLinearLayout.setVisibility(View.VISIBLE);
        } else {
            yearSearchLinearLayout.setVisibility(View.INVISIBLE);
        }
    }



}
