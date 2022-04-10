package com.example.mtgcardsearch.ui.card;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CardViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public CardViewModelFactory(Application mApplication) {
        this.mApplication = mApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CardViewModel(mApplication);
    }
}
