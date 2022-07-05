package com.mk.newsapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mk.newsapp.fragments.BuisnessFragment;
import com.mk.newsapp.fragments.EntertainFragment;
import com.mk.newsapp.fragments.HomeFragment;
import com.mk.newsapp.fragments.SportsFragment;
import com.mk.newsapp.fragments.TechFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabcounts;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcounts = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new HomeFragment();

            case 1:
                return new SportsFragment();

            case 2:
                return new BuisnessFragment();

            case 3:
                return new EntertainFragment();

            case 4:
                return new TechFragment();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabcounts;
    }
}
