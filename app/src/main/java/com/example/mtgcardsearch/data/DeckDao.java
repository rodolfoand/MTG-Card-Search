package com.example.mtgcardsearch.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.Deck;

import java.util.List;

@Dao
public interface DeckDao {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Deck deck);

    @Delete
    void delete(Deck deck);

    @Update
    void update(Deck deck);


    @Query("SELECT * FROM deck_table WHERE deck_id = :deck_id")
    LiveData<Deck> getDeck(String deck_id);

    @Query("SELECT * FROM deck_table ORDER BY deck_id ASC")
    LiveData<List<Deck>> getAlphabetizedDecks();

}
