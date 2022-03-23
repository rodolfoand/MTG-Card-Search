package com.example.mtgcardsearch.ui.cardlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.model.ListSearchResult;

public class CardlistViewModel extends ViewModel {

    private ListRepository listRepository;

    public CardlistViewModel() {
        listRepository = new ListRepository();
    }

    public MutableLiveData<ListSearchResult> getCards(
            String include_extras
            , String include_multilingual
            , String order
            , String page
            , String unique
            , String dir
            , String query){

        return listRepository.getCards(include_extras
                , include_multilingual
                , order
                , page
                , unique
                , dir
                , query);
    }
}
