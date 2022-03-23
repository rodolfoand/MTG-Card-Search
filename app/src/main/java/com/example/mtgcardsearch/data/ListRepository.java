package com.example.mtgcardsearch.data;

import android.util.Log;

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

    public MutableLiveData<ListSearchResult> getCards(
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
                if (response.isSuccessful())
                    mld_listSearchResult.setValue(response.body());
                else
                    mld_listSearchResult.setValue(new ListSearchResult("error", response.message()));
            }

            @Override
            public void onFailure(Call<ListSearchResult> call, Throwable t) {
                Log.d("SEARCH_RESULT", t.getMessage());
            }
        });
        return mld_listSearchResult;
    }
}
