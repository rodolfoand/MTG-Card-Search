package com.example.mtgcardsearch.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mtgcardsearch.model.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Card card);

    @Query("DELETE FROM card_table")
    void deleteAll();

    @Query("SELECT * FROM card_table ORDER BY id ASC")
    LiveData<List<Card>> getAlphabetizedCards();

    @Delete
    void delete(Card card);

    @Query("SELECT * FROM card_table WHERE id = :id")
    LiveData<Card> getCard(String id);
}
