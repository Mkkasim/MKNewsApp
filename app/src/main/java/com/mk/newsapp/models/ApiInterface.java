package com.mk.newsapp.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL="https://newsapi.org/v2/";

    @GET("top-headlines")
    Call<NewsClass> getNews(
        @Query("country") String country,
        @Query("pageSize") int pagessize,
        @Query("apiKey") String apikey

    );

    @GET("everything")
    Call<NewsClass> getSearched(
            @Query("q") String query,
            @Query("apiKey") String apikey

    );

    @GET("top-headlines")
    Call<NewsClass> getCategoryNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("pageSize") int pagessize,
            @Query("apiKey") String apikey

    );

}
