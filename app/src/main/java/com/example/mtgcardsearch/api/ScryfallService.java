package com.example.mtgcardsearch.api;

import com.example.mtgcardsearch.model.AutocompSearchResult;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.SetSearchResult;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScryfallService {

    @GET("/cards/{id}")
    Call<Card> getCard(
            @Path("id")String id);

    @GET("/cards/search?json")
    Call<CardSearchResult> getCards(
            @Query("include_extras")String include_extras
            , @Query("include_multilingual") String include_multilingual
            , @Query("order") String order
            , @Query("page") String page
            , @Query("unique") String unique
            , @Query("dir") String dir
            , @Query("q") String query);

    @GET("/sets/{set}?json")
    Call<SetSearchResult> getSets(
            @Path("set")String set);

    @GET("/cards/autocomplete")
    Call<AutocompSearchResult> getNames(
            @Query("q") String query);
}
