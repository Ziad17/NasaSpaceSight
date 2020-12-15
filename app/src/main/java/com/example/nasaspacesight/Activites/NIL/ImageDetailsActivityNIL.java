package com.example.nasaspacesight.Activites.NIL;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.nasaspacesight.Activites.ThemedActivity;
import com.example.nasaspacesight.Executors.AppExecutors;
import com.example.nasaspacesight.PojoModels.POJO_APOD.SingleApodResponse;
import com.example.nasaspacesight.PojoModels.POJO_NIL.ImageLinks;
import com.example.nasaspacesight.PojoModels.POJO_NIL.Item;
import com.example.nasaspacesight.R;
import com.example.nasaspacesight.Room.RoomDatabase;
import com.example.nasaspacesight.ViewModels.ImageDetailsModelViewNIL;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;


import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.DIRECTORY_PICTURES;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.example.nasaspacesight.Room.DatabaseInfo.DB_NAME;
import static com.example.nasaspacesight.Util.Constants.MY_PERMISSION_REQUEST;

public class ImageDetailsActivityNIL extends ThemedActivity {

    public static final String INTENT_NIL = "nil";
    public static final String INTENT_APOD = "apod";
    public static String INTENT_TAG = "tag";
    RoomDatabase database;

    public static final String TAG = "ImageDetailsActivityNIL";
    LinearLayout main_layout;
    LinearLayout error_layout;

    PhotoView imageView;

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


    ImageDetailsModelViewNIL modelView;
    ImageLinks imagelinks;
    Item itemNIL;
    SingleApodResponse itemAPOD;
    MaterialToolbar toolbar;
    private String image_title;
    private String photographer;
    private String description;
    private String dateCreated;
    private String center;
    private String nasaID;
    ProgressBar prog_bar;


    Bitmap bitmap;
    private Menu myMenu;

    //TODO: Make Downloaded Resources Appears in gallery
    private void BindViews() {
        downloadingButtonLayout = findViewById(R.id.download_buttons_layout);
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
        prog_bar = findViewById(R.id.img_prog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
    }


    @Override
    public void init() {
        modelView = new ViewModelProvider(this).get(ImageDetailsModelViewNIL.class);
        BindViews();
        viewsLoading(true);
        initItemInfo();
        if (isItemNIL()) {
            INTENT_TAG = INTENT_NIL;

            initExtrasVariablesNIL();
            searchForLinks();
            subscribeImageInformationNIL();
            initDownloadHQClickListener();
            initDownloadMQClickListener();
            initToolBar();
        } else {
            INTENT_TAG = INTENT_APOD;
            initExtrasVariablesAPOD();
            getImageInformationAPOD();
            initDownloadHQClickListener();
            initDownloadMQClickListener();
            initToolBar();

        }
    }
    private void initButtonStat(boolean isFav) {
        if (isFav) {
            myMenu.findItem(R.id.fav).setIcon(R.drawable.ic_favorite_black_24dp);
            return;
        }
        myMenu.findItem(R.id.fav).setIcon(R.drawable.ic_favorite_border_black_24dp);
    }
    private void getImageInformationAPOD() {
        imagelinks = new ImageLinks();
        imagelinks.setORIGINAL(itemAPOD.getUrl());
        imagelinks.setLARGE(itemAPOD.getHdurl());
        setAvailableDownloads(imagelinks.ORIGINAL, imagelinks.LARGE);
        supplyViewWithImageInformation(imagelinks);
        setAvailableDownloads(imagelinks.ORIGINAL, imagelinks.LARGE);
    }

    private void searchForLinks() {
        modelView.searchLinksOfItem(itemNIL.getHref());
    }

    private void initItemInfo() {
        try {
            if (getIntent().getStringExtra(TAG).equals(INTENT_NIL)) {
                itemNIL = (Item) getIntent().getSerializableExtra(INTENT_NIL);
                return;
            } else if (getIntent().getStringExtra(TAG).equals(INTENT_APOD)) {
                itemAPOD = (SingleApodResponse) getIntent().getSerializableExtra(INTENT_APOD);
                return;
            }
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isItemNIL() {
        return itemNIL != null;
    }
    private void initExtrasVariablesAPOD() {
        image_title = itemAPOD.getTitle();
        nasaID = "";
        center = itemAPOD.getCopyright();
        dateCreated = itemAPOD.getDate();
        description = itemAPOD.getExplanation();
        photographer = itemAPOD.getCopyright();

    }
    private void initExtrasVariablesNIL() {
        image_title = itemNIL.getData().getTitle();
        nasaID = itemNIL.getData().getNasaId();
        center = itemNIL.getData().getCenter();
        dateCreated = itemNIL.getData().getDateCreated();
        description = itemNIL.getData().getDescription();
        photographer = itemNIL.getData().getPhotographer();
    }
    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_details_menu, menu);
        myMenu = menu;
        database = Room.databaseBuilder(this, RoomDatabase.class, DB_NAME).build();
        if (INTENT_TAG.equals(INTENT_APOD)) {
            modelView.checkAPODItem(itemAPOD.getDate(), database);
            modelView.getAPODItem().observe(this, singleApodResponse -> {
                if(singleApodResponse!=null) {
                    itemAPOD.setFav(true);
                    initButtonStat(singleApodResponse.isFav());
                }
            });
        } else {
            modelView.checkNILItem(itemNIL.getData().getNasaId(),database);
            modelView.getNILItem().observe(this, itemOffline -> {
                if(itemOffline!=null) {
                    itemNIL.setFavorite(true);
                    initButtonStat(itemOffline.isFavorite());
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                shareImage();
                break;
            case R.id.fav:
                convertButtonAndSave();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void convertButtonAndSave() {
        try {
            if (INTENT_TAG.equals(INTENT_NIL)) {
                itemNIL.setFavorite(!itemNIL.isFavorite());
                initButtonStat(itemNIL.isFavorite());
                if (itemNIL.isFavorite()) {
                    modelView.saveImage(itemNIL, database);
                    Toast("Saved");
                    return;
                }
                modelView.removeImage(itemNIL, database);
                Toast("Removed");

            } else if (INTENT_TAG.equals(INTENT_APOD)) {
                itemAPOD.setFav(!itemAPOD.isFav());
                initButtonStat(itemAPOD.isFav());
                if (itemAPOD.isFav()) {
                    modelView.saveImage(itemAPOD, database);
                    Toast("Saved");
                    return;
                }
                modelView.removeImage(itemAPOD, database);
                Toast("Removed");

            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void shareImage() {
        if (bitmap == null) {
            Toast(this.getString(R.string.please_wait_image_to_load));
            return;
        }
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, image_title, null);
        Uri uri = Uri.parse(path);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("image/jpeg");

        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Image Using"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    private void subscribeImageInformationNIL() {
        modelView.getLinksOfItem().observe(this, new Observer<ImageLinks>() {
            @Override
            public void onChanged(ImageLinks imageLinks) {
                if (imageLinks != null) {
                    imagelinks = imageLinks;
                    setAvailableDownloads(imagelinks.ORIGINAL, imagelinks.LARGE);
                    supplyViewWithImageInformation(imagelinks);
                    setAvailableDownloads(imagelinks.ORIGINAL, imagelinks.LARGE);
                } else {
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
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                prog_bar.setVisibility(View.GONE);
                                imageView.setImageDrawable(getDrawable(R.drawable.ic_broken_image_black_24dp));
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                prog_bar.setVisibility(View.GONE);
                                imageView.setImageBitmap(resource);
                                bitmap = resource;
                                return false;
                            }
                        })
                        .apply(new RequestOptions().override(imageView.getWidth(), imageView.getHeight()))
                        .into(imageView);

            } catch (Exception e) {
                imageView.setVisibility(View.VISIBLE);
                prog_bar.setVisibility(View.GONE);
                imageView.setImageDrawable(getDrawable(R.drawable.ic_broken_image_black_24dp));

                Toast("Couldn't Retrieve The Image!");
                Log.e(TAG, "supplyViewWithImageInformation: ", e);
            }
        }
    }

    private void setAvailableDownloads(String Med_link, String HQ_link) {
        download_MQ.setEnabled(false);
        download_HQ.setEnabled(false);

        if (Med_link != null) {
            download_MQ.setEnabled(true);
        }
        if (HQ_link != null) {
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
                downloadAsset(imagelinks.ORIGINAL, image_title);
            }
        });
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(ImageDetailsActivityNIL.this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ImageDetailsActivityNIL.this, new String[]{WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
        }
    }

    private void downloadAsset(final String Link, final String title) {

        //TODO: Push Notification for the downloads Or a progress bar beside the buttons
        requestPermissions();
        AppExecutors.getInstance().getworkIO().submit(() -> {
            writeResourceToStorage(ImageDetailsActivityNIL.this, Link, title);
        });
    }

    private void initDownloadHQClickListener() {
        download_HQ.setOnClickListener(v -> {
            Log.e(TAG, "onClick: " + imagelinks.LARGE);
            downloadAsset(imagelinks.LARGE, image_title);
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
