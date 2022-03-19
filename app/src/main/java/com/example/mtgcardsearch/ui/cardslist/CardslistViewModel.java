package com.example.mtgcardsearch.ui.cardslist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.model.Card;

import java.util.List;

public class CardslistViewModel extends ViewModel {

    private ListRepository listRepository;
    private MutableLiveData<List<Card>> cardList;

    public CardslistViewModel() {
        listRepository = new ListRepository();
    }

    public LiveData<List<Card>> getCards(String query){

        cardList = listRepository.getList(query);
        return cardList;
    }
}
