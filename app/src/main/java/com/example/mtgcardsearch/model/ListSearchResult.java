package com.example.mtgcardsearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListSearchResult {

    @SerializedName("object")
    private String object;

    @SerializedName("total_cards")
    private int total_cards;

    @SerializedName("has_more")
    private boolean has_more;

    @SerializedName("next_page")
    private String next_page;

    @SerializedName("data")
    private List<Card> data;

    @SerializedName("message")
    private String message;

    public ListSearchResult(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
