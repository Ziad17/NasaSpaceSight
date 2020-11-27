package com.example.nasaspacesight.Activites.APOD;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nasaspacesight.Activites.ContextWithInitiativeBehavior;
import com.example.nasaspacesight.Activites.NIL.SearchableFragment;
import com.example.nasaspacesight.ApiData.APOD.ApodClientAPI;
import com.example.nasaspacesight.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SearchDialogAPOD extends Dialog implements ContextWithInitiativeBehavior {


    private static final String START_YEAR = "1997";
    SearchableFragment fragmentsExchanger;

    MaterialButton searchButton;
    MaterialButton backButoon;
    MaterialRadioButton singleSearchRadioButton;
    MaterialRadioButton multiSearchRadioButton;
    TextView startDateEditText;
    TextView endDateEditText;

    TextInputLayout startDateEditTextParent;
    TextInputLayout endDateEditTextParent;

    Context context;
    private boolean MULTI_SEARCH;

    private void BindView() {
        searchButton = findViewById(R.id.button_search);
        backButoon = findViewById(R.id.reset_button);
        startDateEditText = findViewById(R.id.start_date_picker);
        endDateEditText = findViewById(R.id.end_date_picker);
        multiSearchRadioButton = findViewById(R.id.multi_date);
        singleSearchRadioButton = findViewById(R.id.single_date);
        endDateEditTextParent=findViewById(R.id.end_date_picker_parent);
        startDateEditTextParent=findViewById(R.id.start_date_picker_parent);

    }


    public SearchDialogAPOD(Context a, SearchableFragment searchableFragment) {
        super(a, android.R.style.ThemeOverlay_Material_Dialog);
        this.context = a;
        fragmentsExchanger = searchableFragment;
    }

    public static String getTAG() {

        return "SearchDialogNIL";
    }

    private static final String TAG = "SearchDialogNIL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_search_apod);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


    }

    @Override
    public void onStart() {
        super.onStart();
        init();

    }


    @Override
    public void init() {

        BindView();
        initSearchButtonListener();
        initRadioListeners();
        initDatePickersListeners();
        initBackButtonListener();
    }

    private void initDatePickersListeners() {
        //setHintsForTexts(true);
        startDateEditText.setOnKeyListener(null);
        endDateEditText.setOnKeyListener(null);
        startDateEditText.setOnClickListener(this::showDatePicker);
        endDateEditText.setOnClickListener(this::showDatePicker);
    }

    private void showDatePicker(View view)
    {


        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dg=new DatePickerDialog(context,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                (datePicker, i, i1, i2) -> {
                    showConfiguredDate(view,i,i1+1,i2);
                },
                year,month,day);
        Date date = null;

        dg.getDatePicker().setMaxDate(cal.getTimeInMillis());



        dg.show();

    }
    private void showConfiguredDate(View view, int year, int month, int dayOfMonth)
    {
        MaterialTextView editText=(MaterialTextView)view;
        editText.setText(String.format("%d-%02d-%02d", year, month, dayOfMonth));
    }

    private void initRadioListeners() {
        singleSearchRadioButton.setChecked(true);
        hideMultiSearch(false);
        multiSearchRadioButton.setOnCheckedChangeListener((compoundButton, b) -> hideMultiSearch(!b));
        multiSearchRadioButton.setOnCheckedChangeListener((compoundButton, b) -> hideMultiSearch(b));
    }


    private void initBackButtonListener() {
        Button.OnClickListener listener = v -> onBackPressed();
        backButoon.setOnClickListener(listener);
    }

    private void initSearchButtonListener() {
        searchButton.setOnClickListener(view -> {
            searchButtonClicked();
        });

    }

    void searchButtonClicked() {
        //TODO::There is an Error About MultiSearch--look for the documentation
        //TODO: Change the way the data passed to other fragments by caching the results in the DB
        HashMap<String, Object> searchHashMap = new HashMap<String, Object>();
        searchHashMap.put(ApodClientAPI.APOD_API_KEY, ApodClientAPI.APOD_API_KEY_VALUE);
        if(MULTI_SEARCH) {
            try {
                String start_date = startDateEditText.getText().toString().trim();
                String end_date=endDateEditText.getText().toString().trim();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                Date start=simpleDateFormat.parse(start_date);
                Date end=simpleDateFormat.parse(end_date);
                if(start.compareTo(end)>0)
                {
                    Toast.makeText(context,context.getString(R.string.date_must_be_older),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.valueOf(start_date.substring(0,4))<Integer.valueOf(START_YEAR))
                {
                    Toast.makeText(context,context.getString(R.string.date_must_be_after_1997),Toast.LENGTH_SHORT).show();
                    return;
                }

            } catch (ParseException e) {
                Toast.makeText(context,"Error Parsing Date",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return;
            }
            searchHashMap.put(ApodClientAPI.APOD_START_DATE, startDateEditText.getText().toString().trim());
            searchHashMap.put(ApodClientAPI.APOD_END_DATE, endDateEditText.getText().toString().trim());
        }
        else {
            searchHashMap.put(ApodClientAPI.APOD_START_DATE, startDateEditText.getText().toString().trim());
        }
        
        fragmentsExchanger.search(searchHashMap);
        dismiss();

    }


    void hideMultiSearch(boolean isHidden) {
        if (!isHidden) {
            MULTI_SEARCH = false;
            endDateEditTextParent.setVisibility(View.GONE);
            return;
        }
        MULTI_SEARCH = true;
        endDateEditTextParent.setVisibility(View.VISIBLE);
    }


}
