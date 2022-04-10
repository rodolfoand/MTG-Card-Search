package com.example.mtgcardsearch.ui.wishlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.data.RoomRepository;
import com.example.mtgcardsearch.model.Card;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistViewModel extends ViewModel {

    private RoomRepository mRepository;
    private ListRepository listRepository;
    private final LiveData<List<Card>> mAllCardIDs;
    public MutableLiveData<List<Card>> mAllCards;

    public WishlistViewModel(@NonNull Application application) {
        mRepository = new RoomRepository(application);
        mAllCardIDs = mRepository.getAllCards();
        listRepository = new ListRepository();
        mAllCards = new MutableLiveData<>();
    }

    public LiveData<List<Card>> getAllCardIDs() { return mAllCardIDs; }

    public MutableLiveData<Card> getCard(String id){
        return listRepository.getCard(id);
    }

    public void setmAllCards(List<Card> cardList) {

        this.mAllCards.setValue(cardList.stream()
                .map(card -> this.getCard(card.getId()).getValue())
                .collect(Collectors.toList()));
    }

    public void deleteCard(Card card){
        mRepository.delete(card);
    }
}