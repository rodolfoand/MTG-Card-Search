package com.example.mtgcardsearch.ui.decklist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DecklistViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public DecklistViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DecklistViewModel(application);
    }
}
