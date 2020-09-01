package com.example.nasaspacesight.Activites;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nasaspacesight.Executors.AppExecutors;
import com.example.nasaspacesight.POJO.ImageLinks;
import com.example.nasaspacesight.POJO.Item;
import com.example.nasaspacesight.R;
import com.example.nasaspacesight.ViewModels.ImageDetailsModelView;

import java.io.File;


import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.nasaspacesight.Util.Constants.MY_PERMISSION_REQUEST;

public class ImageDetailsActivity extends ThemedActivity {

    public static final String INTENT_ITEM = "Item";

    private static final String TAG = "ImageDetailsActivity";
    LinearLayout main_layout;
    LinearLayout error_layout;

    ImageView imageView;

    Button download_HQ;
    Button download_MQ;

    ScrollView parent;
    TextView descriptionTextView;
    TextView photographerTextView;
    TextView dateCreatedTextView;
    TextView centerTextView;
    TextView nasaIdTextView;
    TextView errorTextView;

    LinearLayout downloadingButtonLayout;


    ImageDetailsModelView modelView;
    ImageLinks imagelinks;
    Item item;
    Toolbar toolbar;
    private String image_title;
    private String photographer;
    private String description;
    private String dateCreated;
    private String center;
    private String nasaID;


    //TODO: Make Downloaded Resources Appears in gallery
    private void BindViews() {
        downloadingButtonLayout=findViewById(R.id.download_buttons_layout);
        parent = findViewById(R.id.parent);
        main_layout = findViewById(R.id.main_layout);
        error_layout = findViewById(R.id.main_error_linear);
        toolbar = findViewById(R.id.image_activity_toolbar);
        imageView = findViewById(R.id.image_imageactivity);
        download_HQ = findViewById(R.id.download_button_hq);
        download_MQ = findViewById(R.id.download_button_mq);
        descriptionTextView = findViewById(R.id.description_textview);
        photographerTextView = findViewById(R.id.photographer_name_textview);
        dateCreatedTextView = findViewById(R.id.date_created_textview);
        centerTextView = findViewById(R.id.center_captured_textview);
        nasaIdTextView = findViewById(R.id.nasa_id_textview);
        errorTextView = findViewById(R.id.main_error_textview);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
    }



    @Override
    public void init() {
        BindViews();
        viewsLoading(true);
        initItemInfo();
        if (isItemNonNull()) {
            initExtrasVariables();
            searchForLinks();
            getImageInformation();
            initDownloadHQClickListener();
            initDownloadMQClickListener();
            initToolBar();

        } else {
            loadError();
            Log.e(TAG, "init: Wrong");
        }
    }
    private void searchForLinks()
    {
        modelView.searchLinksOfItem(item.getHref());
    }
    private void initItemInfo() {
        item = (Item) getIntent().getSerializableExtra(INTENT_ITEM);
        Log.e(TAG, "initItemInfo: " + item.toString());
    }
    private boolean isItemNonNull() {
        return item != null;

    }
    private void initExtrasVariables() {
        image_title = item.getData().getTitle();
        nasaID = item.getData().getNasaId();
        center = item.getData().getCenter();
        dateCreated = item.getData().getDateCreated();
        description = item.getData().getDescription();
        photographer = item.getData().getPhotographer();
        modelView= new ViewModelProvider(this).get(ImageDetailsModelView.class);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        Window window=this.getWindow();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setBackgroundColor(getColor(R.color.AcceptBlue));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Change The Color of the Status Bar contents (battery,clock,etc..)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //Change The Color of the Status Bar Background
        window.setStatusBarColor(getColor(R.color.AcceptBlue));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    private void getImageInformation()
    {
        modelView.getLinksOfItem().observe(this, new Observer<ImageLinks>() {
            @Override
            public void onChanged(ImageLinks imageLinks) {
                if(imageLinks!=null)
                {
                    imagelinks=imageLinks;
                    setAvailableDownloads(imageLinks.ORIGINAL,imageLinks.LARGE);
                    supplyViewWithImageInformation(imageLinks);
                    setAvailableDownloads(imageLinks.ORIGINAL,imageLinks.LARGE);
                    Log.e(TAG, "onChanged: "+imageLinks.ORIGINAL );
                }
                else {
                    Toast("Something Went Wrong");

                }
            }
        });
    }

    private void supplyViewWithImageInformation(final ImageLinks imageLinks) {
        viewsLoading(true);
        if (imageLinks.checkIfLinkExist()) {
            toolbar.setTitle(image_title);
            descriptionTextView.setText(description);
            dateCreatedTextView.setText(dateCreated);
            photographerTextView.setText(photographer);
            nasaIdTextView.setText(nasaID);
            centerTextView.setText(center);
            viewsLoading(false);

            try {
                Glide.with(this)
                        .asBitmap()
                        .load(imageLinks.ORIGINAL)
                        .centerCrop()
                        .apply(new RequestOptions().override(imageView.getWidth(), imageView.getHeight())).placeholder(R.drawable.ic_refresh_black_24dp)
                        .into(imageView);
            } catch (Exception e) {
                Toast("Couldn't Retrieve The Image!");
                Log.e(TAG, "supplyViewWithImageInformation: ", e);
            }
        }
    }

    private void setAvailableDownloads(String Med_link, String HQ_link)
    {
        download_MQ.setEnabled(false);
        download_HQ.setEnabled(false);

        if (Med_link!=null)
        {
            download_MQ.setEnabled(true);
        }
        if (HQ_link!=null)
        {
            download_HQ.setEnabled(true);
        }
    }

    private void viewsLoading(boolean isLoading) {
        //TODO::need to be reviewed
        showProgressBar(isLoading);
        if (isLoading) {
            main_layout.setVisibility(View.INVISIBLE);
        } else {
            parent.setVisibility(View.VISIBLE);
            main_layout.setVisibility(View.VISIBLE);
        }
    }
    private void initDownloadMQClickListener() {
        download_MQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAsset(imagelinks.ORIGINAL,image_title);
            }
        });
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(ImageDetailsActivity.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ImageDetailsActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
        }
    }

    private void downloadAsset(final String Link,final String title) {

        //TODO: Push Notification for the downloads Or a progress bar beside the buttons
        requestPermissions();
        AppExecutors.getInstance().getworkIO().submit(new Runnable() {
            @Override
            public void run() {
                writeResourceToStorage(ImageDetailsActivity.this,Link, title);
            }
        });
    }

    private void initDownloadHQClickListener() {
        download_HQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: "+ imagelinks.LARGE );
                downloadAsset(imagelinks.LARGE,image_title);
            }
        });

    }


    private void loadError() {
        viewsLoading(false);
        ViewGroup.LayoutParams params = main_layout.getLayoutParams();
        params.height = 0;
        main_layout.setLayoutParams(params);
        ViewGroup.LayoutParams params1 = error_layout.getLayoutParams();
        params1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        error_layout.setLayoutParams(params1);
    }

    @Override
    public void showProgressBar(boolean visibility) {
        parent.setVisibility(View.INVISIBLE);
        super.showProgressBar(visibility);
    }

    private boolean writeResourceToStorage(Context context, String link, String Title) {
        try {
            File imageFile = new File(getExternalStoragePublicDirectory(DIRECTORY_DCIM), Title + ".jpg");
            if (!imageFile.exists()) {
                imageFile.mkdir();
            }

            DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri URL = Uri.parse(link);
            DownloadManager.Request request = new DownloadManager.Request(URL);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(true)
                    .setTitle(Title)
                    .setMimeType("image/jpeg")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(DIRECTORY_PICTURES, File.separator + DIRECTORY_DCIM + File.separator + imageFile);
            dm.enqueue(request);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "writeResourceToStorage: ", e);
            return false;
        }

    }











}
