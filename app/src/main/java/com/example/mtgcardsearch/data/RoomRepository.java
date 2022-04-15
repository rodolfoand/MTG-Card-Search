package com.example.mtgcardsearch.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Transaction;

import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardFace;
import com.example.mtgcardsearch.model.CardWithCardfaces;

import java.util.List;

public class RoomRepository {

    private CardDao mCardDao;
    private LiveData<List<Card>> mAllCards;

    public RoomRepository(Application application) {
        CardRoomDatabase db = CardRoomDatabase.getDatabase(application);
        mCardDao = db.cardDao();
        mAllCards = mCardDao.getAlphabetizedCards();
    }

    public LiveData<List<Card>> getAllCards() {
        return mAllCards;
    }

    public void insert(Card card) {
        CardRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.insert(card);
            if (card.getCard_faces() != null) mCardDao.insert(card.getCard_faces());
        });
    }

    public void delete(Card card){
        CardRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.delete(card);
        });
    }

    public LiveData<Card> getCard(String id){
        return mCardDao.getCard(id);
    }

    public void insertCardfaces(List<CardFace> cardfaces) {
        CardRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.insert(cardfaces);
        });
    }

    public LiveData<List<CardWithCardfaces>> getAllCardWithCardfaces(){
        return mCardDao.getCardWithCardfaces();
    }
}
