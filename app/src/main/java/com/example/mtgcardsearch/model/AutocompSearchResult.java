package com.example.mtgcardsearch.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutocompSearchResult extends ListSearchResult{

    @SerializedName("total_values")
    private int total_values;

    @SerializedName("data")
    private List<String> data;

    public AutocompSearchResult(String object, String message) {
        super(object, message);
    }

    public int getTotal_values() {
        return total_values;
    }

    public void setTotal_values(int total_values) {
        this.total_values = total_values;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
