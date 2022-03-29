package com.example.mtgcardsearch.ui.cardlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.model.CardSearchResult;
import com.example.mtgcardsearch.data.PrefDataStore;

public class CardlistViewModel extends ViewModel {

    private ListRepository listRepository;
    private PrefDataStore prefDataStore;

    public CardlistViewModel() {
        listRepository = new ListRepository();
        prefDataStore = PrefDataStore.prefDataStore;
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
}
