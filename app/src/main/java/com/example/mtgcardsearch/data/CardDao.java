package com.example.mtgcardsearch.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardFace;
import com.example.mtgcardsearch.model.CardWithCardfaces;

import java.util.List;
import java.util.Set;

@Dao
public interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Card card);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Card> cards);

    @Query("SELECT * FROM card_table ORDER BY id ASC")
    LiveData<List<Card>> getAlphabetizedCards();

    @Transaction
    @Query("SELECT * FROM card_table")
    LiveData<List<CardWithCardfaces>> getCardWithCardfaces();

    @Delete
    void delete(Card card);

    @Delete
    void delete(List<Card> cards);

    @Query("SELECT * FROM card_table WHERE id = :id")
    LiveData<Card> getCard(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCardFaces(List<CardFace> cardfaces);


}
