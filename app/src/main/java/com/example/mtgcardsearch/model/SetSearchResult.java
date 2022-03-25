package com.example.mtgcardsearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SetSearchResult extends ListSearchResult{

    @SerializedName("data")
    private List<Set> data;

    public SetSearchResult(String object, String message) {
        super(object, message);
    }

    public List<Set> getData() {
        return data;
    }

    public void setData(List<Set> data) {
        this.data = data;
    }
}
