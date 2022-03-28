package com.example.mtgcardsearch.ui.advsearch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.model.SetSearchResult;

public class AdvsearchViewModel extends ViewModel {

    private ListRepository listRepository;

    public AdvsearchViewModel() {
        listRepository = new ListRepository();
    }

    public MutableLiveData<SetSearchResult> getSets(String set){
        return listRepository.getSets(set);
    }
}
