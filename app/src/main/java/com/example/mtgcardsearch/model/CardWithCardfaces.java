package com.example.mtgcardsearch.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CardWithCardfaces {
    @Embedded
    public Card card;

    @Relation(parentColumn = "id",
            entityColumn = "card_id")
    public List<CardFace> cardfaces;
}
