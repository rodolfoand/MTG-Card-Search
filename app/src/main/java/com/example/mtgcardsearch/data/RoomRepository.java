package com.example.mtgcardsearch.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardWithCardfaces;
import com.example.mtgcardsearch.model.ListSearchResult;

import java.util.List;

public class RoomRepository {

    private CardDao mCardDao;

    public RoomRepository(Application application) {
        CardRoomDatabase db = CardRoomDatabase.getDatabase(application);
        mCardDao = db.cardDao();
    }

    public LiveData<List<Card>> getAllCards() {
        return mCardDao.getAlphabetizedCards();
    }

    public void insert(Card card) {
        CardRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.insert(card);
            if (card.getCard_faces() != null) mCardDao.insertCardFaces(card.getCard_faces());
        });
    }

    public void insertCards(List<Card> cards){
        CardRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.insert(cards);
            for (Card c : cards){
                if (c.getCard_faces() != null) mCardDao.insertCardFaces(c.getCard_faces());
            }
            Log.d("MagicAdd", cards.size() + "");
        });
    }

    public void delete(Card card){
        CardRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.delete(card);
        });
    }

    public void delete(List<Card> cards){
        CardRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCardDao.delete(cards);
        });
    }

    public LiveData<Card> getCard(String id){
        return mCardDao.getCard(id);
    }

    public LiveData<List<CardWithCardfaces>> getAllCardWithCardfaces(){
        return mCardDao.getCardWithCardfaces();
    }
}
