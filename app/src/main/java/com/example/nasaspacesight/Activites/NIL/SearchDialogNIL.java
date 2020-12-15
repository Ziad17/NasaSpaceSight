package com.example.nasaspacesight.Activites.NIL;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.nasaspacesight.Activites.ContextWithInitiativeBehavior;
import com.example.nasaspacesight.ApiData.Images.ImagesClientAPI;
import com.example.nasaspacesight.R;

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
public class SearchDialogNIL extends Dialog implements ContextWithInitiativeBehavior {


    private static final int END_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    ArrayList<Integer> yearsArrayStart;
    SearchableFragment fragmentsExchanger;
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

    Context context;


    public SearchDialogNIL(Context a, SearchableFragment searchableFragment) {
        super(a,android.R.style.ThemeOverlay_Material_Dialog);
        this.context=a;
        fragmentsExchanger= searchableFragment;
        // Required empty public constructor
    }

    public static String getTAG() {

        return "SearchDialogNIL";
    }

    private static final String TAG = "SearchDialogNIL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_search);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);




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
        initEnterPressedListener(new ArrayList<EditText>(Arrays.asList(descriptionEditText,keyWordsEditText,locationEditText,photographerEditText,queryEditText,titleEditText)));
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
        unlockYearsCheckBox = findViewById(R.id.date_search);
        queryEditText = findViewById(R.id.query);
        locationEditText = findViewById(R.id.location);
        photographerEditText = findViewById(R.id.photographer_name);
        descriptionEditText = findViewById(R.id.description);
        titleEditText = findViewById(R.id.title_search);
        keyWordsEditText = findViewById(R.id.keywords);
        startYearSpinner=findViewById(R.id.year_start);
        endYearSpinner=findViewById(R.id.year_end);
        resetButton = findViewById(R.id.reset_button);
        searchButton = findViewById(R.id.button_search);
        yearSearchLinearLayout = findViewById(R.id.date_search_layout);
        yearsArrayEnd=new ArrayList<Integer>();
        yearsArrayStart=new ArrayList<Integer>();
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
                dismiss();
               // searchButtonClickedTesting();///////testing/////
            }
        };
        searchButton.setOnClickListener(listener);

    }
    void searchButtonClicked()
    {

        //TODO: Change the way the data passed to other fragments by caching the results in the DB
        HashMap<String,Object> searchHashMap=new HashMap<String,Object>();
        boolean DATE_SEARCH_ENABLED = unlockYearsCheckBox.isChecked();
        searchHashMap.put(ImagesClientAPI.NIL_QUERY,queryEditText.getText().toString().trim());
        searchHashMap.put(ImagesClientAPI.NIL_DESCRIPTION,descriptionEditText.getText().toString().trim());
        searchHashMap.put(ImagesClientAPI.NIL_LOCATION,locationEditText.getText().toString().trim());
        searchHashMap.put(ImagesClientAPI.NIL_TITLE,titleEditText.getText().toString().trim());
        searchHashMap.put(ImagesClientAPI.NIL_PHOTOGRAPHER,photographerEditText.getText().toString().trim());
        searchHashMap.put(ImagesClientAPI.NIL_KEY_WORDS,keyWordsEditText.getText().toString().trim());
        searchHashMap.put(ImagesClientAPI.NIL_PAGE_NUMBER,1);
        searchHashMap.put(ImagesClientAPI.NIL_MEDIA_TYPE,"image");

        if (DATE_SEARCH_ENABLED) {
            searchHashMap.put(ImagesClientAPI.NIL_END_YEAR,Integer.valueOf(endYearSpinner.getSelectedItem().toString().trim()));
            searchHashMap.put(ImagesClientAPI.NIL_END_YEAR,Integer.valueOf(startYearSpinner.getSelectedItem().toString().trim()));
        }
      //  hideFragment();
        Log.e(TAG, "searchButtonClicked:clicked ");
        fragmentsExchanger.search(searchHashMap);


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
