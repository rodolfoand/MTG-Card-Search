package com.example.mtgcardsearch.ui.cardlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CardlistViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public CardlistViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CardlistViewModel(application);
    }
}
