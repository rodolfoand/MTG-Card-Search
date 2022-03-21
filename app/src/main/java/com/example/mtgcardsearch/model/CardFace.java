package com.example.mtgcardsearch.model;

import com.google.gson.annotations.SerializedName;

public class CardFace {
    @SerializedName("name")
    private String name;

    @SerializedName("image_uris")
    private ImageUris image_uris;

    public CardFace(String name, ImageUris image_uris) {
        this.name = name;
        this.image_uris = image_uris;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageUris getImage_uris() {
        return image_uris;
    }

    public void setImage_uris(ImageUris image_uris) {
        this.image_uris = image_uris;
    }
}
