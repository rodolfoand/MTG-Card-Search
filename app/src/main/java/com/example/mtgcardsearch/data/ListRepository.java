package com.example.mtgcardsearch.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mtgcardsearch.api.RetrofitService;
import com.example.mtgcardsearch.api.ScryfallService;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.SetSearchResult;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRepository {

    private ScryfallService scryfallService;
    private MutableLiveData<CardSearchResult> mld_cardSearchResult;
    private MutableLiveData<SetSearchResult> mld_setSearchResult;

    public ListRepository() {
        mld_cardSearchResult = new MutableLiveData<>();
        mld_setSearchResult = new MutableLiveData<>();
    }

    public MutableLiveData<CardSearchResult> getCards(
            String include_extras
            , String include_multilingual
            , String order
            , String page
            , String unique
            , String dir
            , String query){

        scryfallService = RetrofitService.getInterface();

        Call<CardSearchResult> call = scryfallService.getCards(
                include_extras
                , include_multilingual
                , order
                , page
                , unique
                , dir
                , query);
        call.enqueue(new Callback<CardSearchResult>() {
            @Override
            public void onResponse(Call<CardSearchResult> call, Response<CardSearchResult> response) {
                if (response.isSuccessful())
                    mld_cardSearchResult.setValue(response.body());
                else
                    mld_cardSearchResult.setValue(new CardSearchResult("error", response.message()));
            }

            @Override
            public void onFailure(Call<CardSearchResult> call, Throwable t) {
                Log.d("SEARCH_RESULT", t.getMessage());
            }
        });
        Log.d("MagicQueury", call.request().url().toString());
        return mld_cardSearchResult;
    }

    public MutableLiveData<SetSearchResult> getSets(String set){
        scryfallService = RetrofitService.getInterface();

        Call<SetSearchResult> call = scryfallService.getSets(set);

        call.enqueue(new Callback<SetSearchResult>() {
            @Override
            public void onResponse(Call<SetSearchResult> call, Response<SetSearchResult> response) {
                if (response.isSuccessful())
                    mld_setSearchResult.setValue(response.body());
                else
                    mld_setSearchResult.setValue(new SetSearchResult("error", response.message()));
            }

            @Override
            public void onFailure(Call<SetSearchResult> call, Throwable t) {
                Log.d("MTG_SET", t.getMessage());
            }
        });

        return mld_setSearchResult;
    }
}
