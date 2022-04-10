package com.example.mtgcardsearch.ui.card;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.data.RoomRepository;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardSearchResult;

import java.util.List;

public class CardViewModel extends ViewModel {

    private ListRepository listRepository;
    private RoomRepository roomRepository;

    public CardViewModel(Application application){
        listRepository = new ListRepository();
        roomRepository = new RoomRepository(application);
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

    public void insert(Card card){
        roomRepository.insert(card);
    }

    public LiveData<Card> getWishCard(String id){
        return roomRepository.getCard(id);
    }

    public void delete(Card card){
        roomRepository.delete(card);
    }
}