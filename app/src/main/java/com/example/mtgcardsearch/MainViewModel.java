package com.example.mtgcardsearch;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.data.PrefDataStore;
import com.example.mtgcardsearch.model.AutocompSearchResult;

public class MainViewModel extends ViewModel {

    private ListRepository listRepository;
    private PrefDataStore prefDataStore;

    public MainViewModel() {
        listRepository = new ListRepository();
        prefDataStore = PrefDataStore.prefDataStore;
    }

    public MutableLiveData<AutocompSearchResult> getNames(String query){
        return listRepository.getNames(query);
    }

    public String getPrefUserLang(){
        return prefDataStore.getPrefLanguage().blockingFirst();
    }
}
