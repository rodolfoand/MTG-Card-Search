package com.example.mtgcardsearch.ui.deck;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.RoomRepository;
import com.example.mtgcardsearch.model.Deck;

import java.util.Date;

public class DeckViewModel extends ViewModel {

    private RoomRepository roomRepository;

    public DeckViewModel(Application application) {
        roomRepository = new RoomRepository(application);
    }

    public void insert(Deck deck){
        roomRepository.insert(deck);
    }

    public LiveData<Deck> getDeck(String id){
        return roomRepository.getDeck(id);
    }

}
