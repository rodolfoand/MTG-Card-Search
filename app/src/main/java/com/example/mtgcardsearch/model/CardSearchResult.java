package com.example.mtgcardsearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardSearchResult extends ListSearchResult{

    @SerializedName("total_cards")
    private int total_cards;

    @SerializedName("data")
    private List<Card> data;

    public CardSearchResult(String object, String message) {
        super(object, message);
    }

    public int getTotal_cards() {
        return total_cards;
    }

    public void setTotal_cards(int total_cards) {
        this.total_cards = total_cards;
    }

    public List<Card> getData() {
        return data;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }
}
