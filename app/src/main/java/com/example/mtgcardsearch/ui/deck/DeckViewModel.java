package com.example.mtgcardsearch.ui.deck;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.data.RoomRepository;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.model.Deck;

import java.util.Date;

public class DeckViewModel extends ViewModel {

    private RoomRepository roomRepository;
    private ListRepository listRepository;

    public DeckViewModel(Application application) {
        roomRepository = new RoomRepository(application);
        listRepository = new ListRepository();
    }

    public void insert(Deck deck){
        roomRepository.insert(deck);
    }

    public void delete(Deck deck){
        roomRepository.delete(deck);
    }

    public void update(Deck deck){
        roomRepository.update(deck);
    }

    public LiveData<Deck> getDeck(String id){
        return roomRepository.getDeck(id);
    }

    public MutableLiveData<CardSearchResult> getCards(
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
