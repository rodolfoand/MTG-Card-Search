package com.example.mtgcardsearch.ui.setlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.model.SetSearchResult;

    public class SetlistViewModel extends ViewModel {

        private ListRepository listRepository;

        public SetlistViewModel() {
            listRepository = new ListRepository();
        }

        public MutableLiveData<SetSearchResult> getSets(String set){
            return listRepository.getSets(set);
        }
    }