package com.example.mtgcardsearch.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mtgcardsearch.model.Card;
import com.example.mtgcardsearch.model.CardFace;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Card.class, CardFace.class}, version = 1, exportSchema = false)
public abstract class CardRoomDatabase extends RoomDatabase {

    public abstract CardDao cardDao();

    private static volatile CardRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CardRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CardRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardRoomDatabase.class, "card_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
