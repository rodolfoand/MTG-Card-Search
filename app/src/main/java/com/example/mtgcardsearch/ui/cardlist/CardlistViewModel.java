package com.example.mtgcardsearch.ui.cardlist;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.data.RoomRepository;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardFace;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.data.PrefDataStore;
import com.example.mtgcardsearch.model.CardWithCardfaces;

import java.util.List;

public class CardlistViewModel extends ViewModel {

    private ListRepository listRepository;
    private PrefDataStore prefDataStore;
    private RoomRepository roomRepository;

    public CardlistViewModel(Application application) {
        listRepository = new ListRepository();
        prefDataStore = PrefDataStore.prefDataStore;
        roomRepository = new RoomRepository(application);
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

    public int getPrefLayout(){
        return prefDataStore.getPrefLayout().blockingFirst();
    }

    public void setPrefLayout(int layout){
        prefDataStore.setPrefLayout(prefDataStore.PREF_CARDLIST_LAYOUT.getName(), layout);
    }

    public MutableLiveData<Card> getCardbyName(String fuzzy){
        return listRepository.getCardbyName(fuzzy);
    }

    public LiveData<List<Card>> getAllCardIDs(){
        return roomRepository.getAllCards();
    }

    public void insertWish(Card card){
        roomRepository.insert(card);

    }

    public void deleteWish(Card card){
        roomRepository.delete(card);
    }

    public MutableLiveData<Card> getCard(String id){
        return listRepository.getCard(id);
    }

    public LiveData<List<CardWithCardfaces>> getAllCardWithCardfaces(){
        return roomRepository.getAllCardWithCardfaces();
    }
}
