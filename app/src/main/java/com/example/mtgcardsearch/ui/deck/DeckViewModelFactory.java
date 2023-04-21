package com.example.mtgcardsearch.ui.deck;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DeckViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public DeckViewModelFactory(Application mApplication) {
        this.mApplication = mApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DeckViewModel(mApplication);
    }
}
