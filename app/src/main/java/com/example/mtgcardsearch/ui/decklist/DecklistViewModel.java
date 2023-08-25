package com.example.mtgcardsearch.ui.decklist;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.RoomRepository;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.Deck;

import java.util.List;

public class DecklistViewModel extends ViewModel {

    private RoomRepository roomRepository;

    public DecklistViewModel(Application application) {
        roomRepository = new RoomRepository(application);
    }

    public LiveData<List<Deck>> getAllDecks(){
        return roomRepository.getAllDecks();
    }

    public void deleteDecks(List<Deck> decks){
        roomRepository.deleteDecks(decks);
    }

}
