package com.example.nasaspacesight.Activites;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nasaspacesight.Activites.APOD.ResultsFragmentAPOD;
import com.example.nasaspacesight.Activites.NIL.ResultsFragmentNIL;
import com.example.nasaspacesight.Activites.Offline.FavoritesFragment;
import com.example.nasaspacesight.Activites.Offline.ResultsFragmentOfflineNIL;
import com.example.nasaspacesight.Activites.Offline.ResultsFragmentOfflineResultsAPOD;
import com.example.nasaspacesight.R;
import com.example.nasaspacesight.Room.RoomDatabase;
import com.example.nasaspacesight.Util.Constants;
import com.example.nasaspacesight.ViewModels.ImageDetailsModelViewNIL;
import com.google.android.material.navigation.NavigationView;


import static com.example.nasaspacesight.Room.DatabaseInfo.DB_NAME;


public class MainActivity extends ThemedActivity  {


    NavigationView navigationView;
    private ResultsFragmentAPOD resultsFragmentAPOD;

    private TextView titleTextView;
    PlaceHolder placeHolderfrgment;

    IonBackPressed backStackedFrag;

    public Toolbar getMainToolbar() {
        return mainToolbar;
    }

    Toolbar mainToolbar;
    private androidx.appcompat.app.ActionBarDrawerToggle toggleButton;
    private DrawerLayout drawerLayout;
    InfoFragment infoFragment;
    FragmentManager fragmentManager;
    ResultsFragmentNIL resultsFragmentNIL;
    FavoritesFragment favoritesFragment;
    ResultsFragmentOfflineNIL resultsFragmentNILoffline;
    ResultsFragmentOfflineResultsAPOD resultsFragmentAPODoffline;


    private static final String TAG = "MainActivity";







    //testing variables
    ImageDetailsModelViewNIL imageDetailsModelViewNIL;
    RoomDatabase roomDatabase;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        else if(backStackedFrag.onBackPressedI())
        {

            final MainActivity ref = this;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                    .setTitle("Exit?")
                    .setMessage("Do You Want To Exit? :(")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dialog.dismiss();
                        ref.finish();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

    }

    private void BindViews() {
        titleTextView=findViewById(R.id.title);
        mainToolbar = findViewById(R.id.toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        navigationView= findViewById(R.id.nav_view);






    }

    @Override
    public void init() {
        BindViews();
        initToolBar();
        initToggleButton();
        initNavigationItemSelectedListener();
        initFragmentManager();
        redirectToCertainFragment(placeHolderfrgment,R.menu.empty_menu,getString(R.string.nasa_space_sight));

    }


    @Deprecated
    void dropAllRecords()
    {
        RoomDatabase roomDatabase= Room.databaseBuilder(this,RoomDatabase.class,DB_NAME).allowMainThreadQueries().build();
        roomDatabase.getDao().deleteAllNIL();
        roomDatabase.getDao().deleteAllAPOD();


    }
    private void initFragmentManager()
    {
        fragmentManager=getSupportFragmentManager();
        resultsFragmentNIL=new ResultsFragmentNIL();
        resultsFragmentAPOD =new ResultsFragmentAPOD();
        infoFragment=new InfoFragment();
        favoritesFragment=new FavoritesFragment();
        placeHolderfrgment=new PlaceHolder();
        resultsFragmentAPODoffline=new ResultsFragmentOfflineResultsAPOD();
        resultsFragmentNILoffline=new ResultsFragmentOfflineNIL();

    }

    private void initToggleButton()
    {
        toggleButton= new ActionBarDrawerToggle(this, drawerLayout, mainToolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggleButton);
        toggleButton.syncState();

    }

    private void initNavigationItemSelectedListener()
    {
        navigationView.setNavigationItemSelectedListener(menuItem -> {

            resultsFragmentNIL=new ResultsFragmentNIL();

            switch (menuItem.getItemId())
            {
                case R.id.images:
                    redirectToImagesFragment();
                    break;

                case R.id.apod:
                    redirectToApodFragment();
                    break;

                case R.id.favs_imgs:
                    redirectToImagesFragmentOffline();
                    break;
                case R.id.favs_apod:
                    redirectToApodFragmentOffline();

                    break;
                case R.id.info:
                    redirectToInfoFragment();
                    break;

                case R.id.settings:
                    redirectToSettingsFragment();
                    break;
                case R.id.github:
                    redirectToGithub();
                    break;

            }
            drawerLayout.closeDrawer(navigationView);
            return false;
        });
    }


    private void redirectToGithub()
    {

        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB_LINK));
        startActivity(intent);

    }

    private void redirectToSettingsFragment()
    {

    }

    private void redirectToInfoFragment()
    {
        redirectToCertainFragment(infoFragment,R.menu.empty_menu,getString(R.string.about));
    }

    private void redirectToApodFragment()
    {

        //for now we will use nil_menu
        redirectToCertainFragment(resultsFragmentAPOD,R.menu.images_menu,getString(R.string.photo_of_the_day));
    }
    private void redirectToApodFragmentOffline()
    {

        //for now we will use nil_menu
        redirectToCertainFragment(resultsFragmentAPODoffline,R.menu.empty_menu,getString(R.string.photo_of_the_day));
    }

    private void redirectToImagesFragmentOffline()
    {
        redirectToCertainFragment(resultsFragmentNILoffline,R.menu.empty_menu,getString(R.string.images));
    }


    private void redirectToImagesFragment()
    {
        redirectToCertainFragment(resultsFragmentNIL,R.menu.images_menu,getString(R.string.images));
        assignBackStackedFragment(resultsFragmentNIL);
    }
    void assignBackStackedFragment(IonBackPressed _backStackedFrag)
    {
        backStackedFrag=_backStackedFrag;
    }


    void redirectToCertainFragment(Fragment frag_target, int target_menu,String menu_title)
    {
        fragmentManager.beginTransaction().replace(R.id.main_fragment,frag_target,"TAG").commit();
        mainToolbar.getMenu().clear();
        setToolBarTitle(menu_title);
        getMenuInflater().inflate(target_menu,mainToolbar.getMenu());
    }

    private void setToolBarTitle(String string)
    {
        titleTextView.setText(string);

    }


    private void initToolBar() {
    setSupportActionBar(mainToolbar);

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggleButton.onConfigurationChanged(newConfig);
        toggleButton.syncState();
    }


    public interface IonBackPressed
    {
        boolean onBackPressedI();

    }


}


