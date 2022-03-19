package com.example.mtgcardsearch.api;

import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.ListSearchResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ScryfallService {
    @GET("/cards/search")
    Call<ListSearchResult> getCards(@Query("q")String query);
}
