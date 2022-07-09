package com.mk.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.mk.newsapp.adapters.PagerAdapter;
import com.mk.newsapp.databinding.ActivityMainBinding;
import com.mk.newsapp.sessionmanager.SessionManagement;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding  binding;

    PagerAdapter adapter;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setPagerAdapter();
        setTabLayout();
        SessionManagement.init(this);

        auth = FirebaseAuth.getInstance();

        binding.logout.setOnClickListener(view -> {

            SessionManagement.setLogin(false);
            FirebaseAuth.getInstance().signOut();
            Intent in = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(in);
            finish();

        });


    }

    private void setPagerAdapter() {

        adapter = new PagerAdapter(getSupportFragmentManager(),5);
        binding.fcont.setAdapter(adapter);
    }

    private void setTabLayout() {

        binding.include.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.fcont.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0||tab.getPosition()==1||tab.getPosition()==2||tab.getPosition()==3||tab.getPosition()==4){

                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.fcont.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.include));

    }

}