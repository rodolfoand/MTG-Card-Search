package com.example.mtgcardsearch.data;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.mtgcardsearch.api.RetrofitService;
import com.example.mtgcardsearch.api.ScryfallService;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.ListSearchResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListRepository {

    private ScryfallService scryfallService;
    private MutableLiveData<List<Card>> listResponseLiveData;

    public ListRepository() {
        listResponseLiveData = new MutableLiveData<>();
    }
    public MutableLiveData<List<Card>> getList(String query){

        scryfallService = RetrofitService.getInterface();

        Call<ListSearchResult> call = scryfallService.getCards(query);
        call.enqueue(new Callback<ListSearchResult>() {
            @Override
            public void onResponse(Call<ListSearchResult> call, Response<ListSearchResult> response) {
                if (response.isSuccessful()) {
                    MutableLiveData<ListSearchResult> mldListSearchResult = new MutableLiveData<>();
                    mldListSearchResult.setValue(response.body());

                    listResponseLiveData.setValue(mldListSearchResult.getValue().getData());
                }
            }

            @Override
            public void onFailure(Call<ListSearchResult> call, Throwable t) {

            }
        });
        return listResponseLiveData;
    }
}
