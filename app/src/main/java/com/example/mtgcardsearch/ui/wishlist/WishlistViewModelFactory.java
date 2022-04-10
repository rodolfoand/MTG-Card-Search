package com.example.mtgcardsearch.ui.wishlist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class WishlistViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public WishlistViewModelFactory(Application mApplication) {
        this.mApplication = mApplication;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WishlistViewModel(mApplication);
    }
}
