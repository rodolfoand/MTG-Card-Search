package com.example.mtgcardsearch.data;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import java.util.Locale;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


public class PrefDataStore {

    public static PrefDataStore prefDataStore = new PrefDataStore();
    public RxDataStore<Preferences> dataStore;
    public Preferences.Key<Integer> PREF_CARDLIST_LAYOUT;
    public Preferences.Key<String> PREF_USER_LANGUAGE;

    public PrefDataStore() {}

    public void setContext(Context context) {
        this.dataStore = new RxPreferenceDataStoreBuilder(context, /*name=*/ "settings").build();
        PREF_CARDLIST_LAYOUT = PreferencesKeys.intKey("cardlistLayoutKey");
        setPrefLayout(PREF_CARDLIST_LAYOUT.getName(), 1);
        PREF_USER_LANGUAGE = PreferencesKeys.stringKey("userLanguageKey");
        setPrefLanguage(PREF_USER_LANGUAGE.getName(), Locale.getDefault().getLanguage().toString());
    }

    public void setPrefLayout(String keyName, Integer value){
        PREF_CARDLIST_LAYOUT = PreferencesKeys.intKey(keyName);

        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_CARDLIST_LAYOUT, value);
            return Single.just(mutablePreferences);
        });
    }

    public Flowable<Integer> getPrefLayout() {
        return dataStore.data().map(prefs -> prefs.get(PREF_CARDLIST_LAYOUT));
    }

    public void setPrefLanguage(String keyName, String value){
        PREF_USER_LANGUAGE = PreferencesKeys.stringKey(keyName);

        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_USER_LANGUAGE, value);
            return Single.just(mutablePreferences);
        });
    }

    public Flowable<String> getPrefLanguage() {
        return dataStore.data().map(prefs -> prefs.get(PREF_USER_LANGUAGE));
    }
}
