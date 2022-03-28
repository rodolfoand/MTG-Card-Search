package com.example.mtgcardsearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Set {

    @SerializedName("name")
    private String name;

    @SerializedName("search_uri")
    private String search_uri;

    @SerializedName("card_count")
    private String card_count;

    private Card expensive_card;

    @SerializedName("icon_svg_uri")
    private String icon_svg_uri;

    @SerializedName("released_at")
    private Date released_at;

    @SerializedName("code")
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearch_uri() {
        return search_uri;
    }

    public void setSearch_uri(String search_uri) {
        this.search_uri = search_uri;
    }

    public String getCard_count() {
        return card_count;
    }

    public void setCard_count(String card_count) {
        this.card_count = card_count;
    }

    public Card getExpensive_card() {
        return expensive_card;
    }

    public void setExpensive_card(Card expensive_card) {
        this.expensive_card = expensive_card;
    }

    public String getIcon_svg_uri() {
        return icon_svg_uri;
    }

    public void setIcon_svg_uri(String icon_svg_uri) {
        this.icon_svg_uri = icon_svg_uri;
    }

    public Date getReleased_at() {
        return released_at;
    }

    public void setReleased_at(Date released_at) {
        this.released_at = released_at;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
