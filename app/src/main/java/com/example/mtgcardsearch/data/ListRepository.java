package com.example.mtgcardsearch.data;

import androidx.lifecycle.MutableLiveData;

import com.example.mtgcardsearch.api.RetrofitService;
import com.example.mtgcardsearch.api.ScryfallService;
import com.example.mtgcardsearch.model.ListSearchResult;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRepository {

    private ScryfallService scryfallService;
    private MutableLiveData<ListSearchResult> mld_listSearchResult;

    public ListRepository() {
        mld_listSearchResult = new MutableLiveData<>();
    }

    public MutableLiveData<ListSearchResult> getList(String query){

        scryfallService = RetrofitService.getInterface();

        Call<ListSearchResult> call = scryfallService.getCards(query);
        call.enqueue(new Callback<ListSearchResult>() {
            @Override
            public void onResponse(Call<ListSearchResult> call, Response<ListSearchResult> response) {
                if (response.isSuccessful()) {
                    mld_listSearchResult.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<ListSearchResult> call, Throwable t) {

            }
        });
        return mld_listSearchResult;
    }

    public MutableLiveData<ListSearchResult> getNextList(
            String include_extras
            , String include_multilingual
            , String order
            , String page
            , String unique
            , String query){

        scryfallService = RetrofitService.getInterface();

        Call<ListSearchResult> call = scryfallService.getNextPage(
                include_extras
                , include_multilingual
                , order
                , page
                , unique
                , query);
        call.enqueue(new Callback<ListSearchResult>() {
            @Override
            public void onResponse(Call<ListSearchResult> call, Response<ListSearchResult> response) {
                mld_listSearchResult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ListSearchResult> call, Throwable t) {

            }
        });
        return mld_listSearchResult;
    }

    public MutableLiveData<ListSearchResult> getList(
            String include_extras
            , String include_multilingual
            , String order
            , String page
            , String unique
            , String dir
            , String query){

        scryfallService = RetrofitService.getInterface();

        Call<ListSearchResult> call = scryfallService.getList(
                include_extras
                , include_multilingual
                , order
                , page
                , unique
                , dir
                , query);
        call.enqueue(new Callback<ListSearchResult>() {
            @Override
            public void onResponse(Call<ListSearchResult> call, Response<ListSearchResult> response) {
                mld_listSearchResult.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ListSearchResult> call, Throwable t) {

            }
        });
        return mld_listSearchResult;
    }
}
