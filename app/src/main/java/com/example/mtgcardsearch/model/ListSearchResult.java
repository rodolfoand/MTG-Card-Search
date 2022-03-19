package com.example.mtgcardsearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListSearchResult {

    @SerializedName("total_cards")
    private int total_cards;

    @SerializedName("has_more")
    private boolean has_more;

    @SerializedName("next_page")
    private String next_page;

    @SerializedName("data")
    private List<Card> data;

    public ListSearchResult(int total_cards, boolean has_more, String next_page, List<Card> data) {
        this.total_cards = total_cards;
        this.has_more = has_more;
        this.next_page = next_page;
        this.data = data;
    }

    public int getTotal_cards() {
        return total_cards;
    }

    public void setTotal_cards(int total_cards) {
        this.total_cards = total_cards;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public String getNext_page() {
        return next_page;
    }

    public void setNext_page(String next_page) {
        this.next_page = next_page;
    }

    public List<Card> getData() {
        return data;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }
}
