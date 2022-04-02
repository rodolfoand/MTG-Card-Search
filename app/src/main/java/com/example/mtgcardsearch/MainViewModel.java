package com.example.mtgcardsearch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.model.AutocompSearchResult;

public class MainViewModel extends ViewModel {

    private ListRepository listRepository;

    public MainViewModel() {
        listRepository = new ListRepository();
    }

    public MutableLiveData<AutocompSearchResult> getNames(String query){
        return listRepository.getNames(query);
    }
}
