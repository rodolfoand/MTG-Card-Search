package com.example.mtgcardsearch.api;

import com.example.mtgcardsearch.model.ListSearchResult;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ScryfallService {
    @GET("/cards/search")
    Call<ListSearchResult> getCards(@Query("q")String query);

    @GET("/cards/search?json")
    Call<ListSearchResult> getNextPage(
            @Query("include_extras")String include_extras
            , @Query("include_multilingual") String include_multilingual
            , @Query("order") String order
            , @Query("page") String page
            , @Query("unique") String unique
            , @Query("q") String query);
}
