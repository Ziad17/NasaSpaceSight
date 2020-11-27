package com.example.nasaspacesight.Adapters;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;

    public List<Drawable> getIcons() {
        return icons;
    }

    List<Drawable> icons;

    public void setFragments(List<Fragment> fragments, Drawable icon) {
        this.fragments = fragments;
        icons.add(icon);
        notifyDataSetChanged();
    }

    public void addFragment(Fragment fragment,Drawable drawable)
    {
        fragments.add(fragment);
        icons.add(drawable);
    }

    public FavoritesAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragments=new ArrayList<Fragment>();
        icons=new ArrayList<Drawable>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(fragments.size()>0) {
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(fragments.size()>0) return fragments.size();
        return 0;
    }
}
