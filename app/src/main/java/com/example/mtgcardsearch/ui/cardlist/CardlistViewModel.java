package com.example.mtgcardsearch.ui.cardlist;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.ListSearchResult;

import java.util.List;

public class CardlistViewModel extends ViewModel {

    private ListRepository listRepository;

    public CardlistViewModel() {
        listRepository = new ListRepository();
    }

    public MutableLiveData<ListSearchResult> getListSearchResult(String query){
        return listRepository.getList(query);
    }

    public MutableLiveData<ListSearchResult> getNextListSearchResult(
            String include_extras
            , String include_multilingual
            , String order
            , String page
            , String unique
            , String query){

        return listRepository.getNextList(include_extras
                , include_multilingual
                , order
                , page
                , unique
                , query);
    }
}
