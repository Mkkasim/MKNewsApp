package com.mk.newsapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mk.newsapp.R;
import com.mk.newsapp.adapters.RecyclerAdapter;
import com.mk.newsapp.databinding.FragmentHomeBinding;
import com.mk.newsapp.models.ApiUtilities;
import com.mk.newsapp.models.ModelClass;
import com.mk.newsapp.models.NewsClass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    ArrayList<ModelClass> modelClassArrayList;
    RecyclerAdapter adapter;
    String country="in";

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        modelClassArrayList= new ArrayList<>();
        binding.recychome.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new RecyclerAdapter(getContext(),modelClassArrayList);

        binding.recychome.setAdapter(adapter);

        findNews();

    }

    private void findNews() {

        String api = getString(R.string.new_api_key);
        ApiUtilities.getApiInterface().getNews(country,100,api).enqueue(new Callback<NewsClass>() {
            @Override
            public void onResponse(Call<NewsClass> call, Response<NewsClass> response) {
                if (response.isSuccessful()){
                    modelClassArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsClass> call, Throwable t) {

            }
        });

    }
}