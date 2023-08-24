package com.example.mtgcardsearch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "deck_table")
public class Deck {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "deck_id")
    private int deck_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "format")
    private String format;

    @ColumnInfo(name = "notes")
    private String notes;

    @ColumnInfo(name = "maindeck")
    private String maindeck;

    @ColumnInfo(name = "sideboard")
    private String sideboard;

    @ColumnInfo(name = "maybeboard")
    private String maybeboard;

    @ColumnInfo(name = "created_on")
    private Date created_on;

    public Deck() {
    }

    public int getDeck_id() {
        return deck_id;
    }

    public void setDeck_id(int deck_id) {
        this.deck_id = deck_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMaindeck() {
        return maindeck;
    }

    public void setMaindeck(String maindeck) {
        this.maindeck = maindeck;
    }

    public String getSideboard() {
        return sideboard;
    }

    public void setSideboard(String sideboard) {
        this.sideboard = sideboard;
    }

    public String getMaybeboard() {
        return maybeboard;
    }

    public void setMaybeboard(String maybeboard) {
        this.maybeboard = maybeboard;
    }

    public Date getCreated_on() {
        return created_on;
    }

    public void setCreated_on(Date created_on) {
        this.created_on = created_on;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return name.equals(deck.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Deck{" +
                "deck_id=" + deck_id +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", notes='" + notes + '\'' +
                ", maindeck='" + maindeck + '\'' +
                ", sideboard='" + sideboard + '\'' +
                ", maybeboard='" + maybeboard + '\'' +
                '}';
    }
}