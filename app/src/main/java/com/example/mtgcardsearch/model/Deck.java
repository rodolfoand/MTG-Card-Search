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

    @ColumnInfo(name = "updated_on")
    private Date updated_on;

    @ColumnInfo(name = "main_lines")
    private String main_lines;

    @ColumnInfo(name = "main_error")
    private String main_error;

    @ColumnInfo(name = "side_lines")
    private String side_lines;

    @ColumnInfo(name = "side_error")
    private String side_error;

    @ColumnInfo(name = "maybe_lines")
    private String maybe_lines;

    @ColumnInfo(name = "maybe_error")
    private String maybe_error;

    public Deck() {
    }

    public Deck(String name, String format, String notes, String maindeck, String sideboard, String maybeboard, Date created_on, Date updated_on) {
        this.name = name;
        this.format = format;
        this.notes = notes;
        this.maindeck = maindeck;
        this.sideboard = sideboard;
        this.maybeboard = maybeboard;
        this.created_on = created_on;
        this.updated_on = updated_on;
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

    public Date getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }

    public String getMain_lines() {
        return main_lines;
    }

    public void setMain_lines(String main_lines) {
        this.main_lines = main_lines;
    }

    public String getMain_error() {
        return main_error;
    }

    public void setMain_error(String main_error) {
        this.main_error = main_error;
    }

    public String getSide_lines() {
        return side_lines;
    }

    public void setSide_lines(String side_lines) {
        this.side_lines = side_lines;
    }

    public String getSide_error() {
        return side_error;
    }

    public void setSide_error(String side_error) {
        this.side_error = side_error;
    }

    public String getMaybe_lines() {
        return maybe_lines;
    }

    public void setMaybe_lines(String maybe_lines) {
        this.maybe_lines = maybe_lines;
    }

    public String getMaybe_error() {
        return maybe_error;
    }

    public void setMaybe_error(String maybe_error) {
        this.maybe_error = maybe_error;
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
