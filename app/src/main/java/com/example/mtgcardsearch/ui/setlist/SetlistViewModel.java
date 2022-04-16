package com.example.mtgcardsearch.ui.setlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mtgcardsearch.data.ListRepository;
import com.example.mtgcardsearch.data.PrefDataStore;
import com.example.mtgcardsearch.model.SetSearchResult;

public class SetlistViewModel extends ViewModel {

    private ListRepository listRepository;
    private PrefDataStore prefDataStore;

    public SetlistViewModel() {
        listRepository = new ListRepository();
        prefDataStore = PrefDataStore.prefDataStore;
    }

    public MutableLiveData<SetSearchResult> getSets(String set){
        return listRepository.getSets(set);
    }

    public String getPrefSetlistFilter(){
        return prefDataStore.getPrefString(prefDataStore.PREF_SETLIST_FILTER, "expansion").blockingFirst();
    }

    public void setPrefSetlistFilter(String filter){
        prefDataStore.setPrefString(prefDataStore.PREF_SETLIST_FILTER, filter);
    }
}