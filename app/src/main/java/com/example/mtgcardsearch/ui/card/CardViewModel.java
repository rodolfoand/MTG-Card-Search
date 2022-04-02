package com.example.mtgcardsearch.ui.card;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardSearchResult;

public class CardViewModel extends ViewModel {

    private ListRepository listRepository;

    public CardViewModel(){
        listRepository = new ListRepository();
    }

    public MutableLiveData<Card> getCard(String id){
        return listRepository.getCard(id);
    }

    public MutableLiveData<CardSearchResult> getCards(
            String include_extras
            , String include_multilingual
            , String order
            , String page
            , String unique
            , String dir
            , String query) {
        return listRepository.getCards(include_extras
                , include_multilingual
                , order
                , page
                , unique
                , dir
                , query);
    }
}