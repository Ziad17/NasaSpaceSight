package com.example.nasaspacesight.Activites;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.deepan.pieprogress.PieProgress;
import com.example.nasaspacesight.R;

public abstract class ThemedActivity extends AppCompatActivity implements ContextWithInitiativeBehavior {

    protected String TAG;

    PieProgress progressBar;
    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout=(ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_themed,null);
        FrameLayout frameLayout=constraintLayout.findViewById(R.id.base_fragment);
        progressBar=constraintLayout.findViewById(R.id.base_progress_bar);

        getLayoutInflater().inflate(layoutResID,frameLayout,true);
        super.setContentView(constraintLayout);

        init();

    }

    public void showProgressBar(boolean visibility)
    {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
    }
    public void Toast(String msg)
    {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}

