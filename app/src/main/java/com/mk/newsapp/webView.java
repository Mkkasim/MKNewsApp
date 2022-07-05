package com.mk.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebViewClient;

import com.mk.newsapp.databinding.ActivityMainBinding;
import com.mk.newsapp.databinding.ActivityWebViewBinding;

public class webView extends AppCompatActivity {

    ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent in = getIntent();
        String url = in.getStringExtra("url");

        binding.webview.setWebViewClient(new WebViewClient());
        binding.webview.loadUrl(url);

    }
}