package com.example.mtgcardsearch.model;

import com.google.gson.annotations.SerializedName;

public abstract class ListSearchResult {

    @SerializedName("object")
    private String object;

    @SerializedName("has_more")
    private boolean has_more;

    @SerializedName("next_page")
    private String next_page;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
