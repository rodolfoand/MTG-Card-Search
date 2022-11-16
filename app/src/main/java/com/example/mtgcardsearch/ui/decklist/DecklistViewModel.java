package com.example.mtgcardsearch.ui.decklist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DecklistViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DecklistViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mydecks fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
