package com.example.mtgcardsearch.ui.cardlist;

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

    public LiveData<List<Card>> getCards(String query){
        return listRepository.getCards(query);
    }

    public MutableLiveData<ListSearchResult> getListSearchResult(String query){
        return listRepository.getList(query);
    }

    public MutableLiveData<ListSearchResult> getNextListSearchResult(String next){
        return listRepository.getNextList(next);
    }
}
