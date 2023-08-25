package com.example.mtgcardsearch.ui.home;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.data.RoomRepository;
import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.Deck;
import com.example.mtgcardsearch.model.SetSearchResult;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private RoomRepository roomRepository;

    public HomeViewModel(Application application) {
        roomRepository = new RoomRepository(application);
    }

    public LiveData<List<Card>> getCardIDs(){
        return  roomRepository.getAllCards();
    }

    public LiveData<List<Deck>> getDecks() {
        return roomRepository.getAllDecks();
    }

}