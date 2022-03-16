package com.example.mtgcardsearch.ui.mydecks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MydecksViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public MydecksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mydecks fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
