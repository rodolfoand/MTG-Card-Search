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
    public Preferences.Key<String> PREF_WISHLIST_ORDER;
    public Preferences.Key<String> PREF_SETLIST_FILTER;

    public PrefDataStore() {}

    public void setContext(Context context) {
        this.dataStore = new RxPreferenceDataStoreBuilder(context, /*name=*/ "settings").build();
        PREF_CARDLIST_LAYOUT = PreferencesKeys.intKey("cardlistLayoutKey");
        PREF_USER_LANGUAGE = PreferencesKeys.stringKey("userLanguageKey");
        PREF_WISHLIST_ORDER = PreferencesKeys.stringKey("wishlistOrderKey");
        PREF_SETLIST_FILTER = PreferencesKeys.stringKey("setlistFilterKey");
    }

    public Flowable<String> getPrefString(Preferences.Key<String> keyPref, String def_value){
        return dataStore.data()
                .map(prefs -> (prefs.get(keyPref) == null)
                        ? def_value
                        : prefs.get(keyPref));
    }

    public void setPrefString(Preferences.Key<String> keyPref, String value){
        keyPref = PreferencesKeys.stringKey(keyPref.getName());

        Preferences.Key<String> finalKeyPref = keyPref;
        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(finalKeyPref, value);
            return Single.just(mutablePreferences);
        });
    }

    public Flowable<Integer> getPrefInteger(Preferences.Key<Integer> keyPref, Integer def_value){
        return dataStore.data()
                .map(prefs -> (prefs.get(keyPref) == null)
                        ? def_value
                        : prefs.get(keyPref));
    }

    public void setPrefInteger(Preferences.Key<Integer> keyPref, Integer value){
        keyPref = PreferencesKeys.intKey(keyPref.getName());

        Preferences.Key<Integer> finalKeyPref = keyPref;
        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(finalKeyPref, value);
            return Single.just(mutablePreferences);
        });
    }
}