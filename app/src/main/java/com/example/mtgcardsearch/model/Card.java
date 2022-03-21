package com.example.mtgcardsearch.model;

import android.util.Log;
import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Card {

    @SerializedName("name")
    private String name;

    @SerializedName("layout")
    private String layout;

    @SerializedName("image_uris")
    private ImageUris image_uris;

    private int face_position;

    private String image_url;

    @SerializedName("card_faces")
    private ArrayList<CardFace> card_faces;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public ImageUris getImage_uris() {
        return image_uris;
    }

    public void setImage_uris(ImageUris image_uris) {
        this.image_uris = image_uris;
    }

    public int getFace_position() {
        return face_position;
    }

    public void setFace_position() {
        if (this.hasImageInCardFaces()
                && this.getFace_position() < (this.getCard_faces().size() - 1)) {
            this.face_position++;
        }
        else this.face_position = 0;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url() {
        if (this.hasImageInCardFaces()) {
            this.image_url = this.getCard_faces()
                    .get(this.getFace_position())
                    .getImage_uris()
                    .getLarge();
        } else {
            this.image_url = this.getImage_uris().getLarge();
        }
    }

    public ArrayList<CardFace> getCard_faces() {
        return card_faces;
    }

    public void setCard_faces(ArrayList<CardFace> card_faces) {
        this.card_faces = card_faces;
    }

    public boolean hasImageInCardFaces() {
        return (this.getCard_faces() != null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return name.equals(card.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                '}';
    }
}
