package com.example.mtgcardsearch.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;
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

    @SerializedName("uri")
    private String uri;

    @SerializedName("id")
    private String id;

    @SerializedName("type_line")
    private String type_line;

    @SerializedName("oracle_text")
    private String oracle_text;

    @SerializedName("loyalty")
    private String loyalty;

    @SerializedName("artist")
    private String artist;

    @SerializedName("legalities")
    private Map<String, String> legalities;

    @SerializedName("set_name")
    private String set_name;

    @SerializedName("set")
    private String set;

    @SerializedName("collector_number")
    private String collector_number;

    @SerializedName("rarity")
    private String rarity;

    @SerializedName("lang")
    private String lang;

    @SerializedName("set_search_uri")
    private String set_search_uri;

    @SerializedName("prints_search_uri")
    private String prints_search_uri;




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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_line() {
        return type_line;
    }

    public void setType_line(String type_line) {
        this.type_line = type_line;
    }

    public String getOracle_text() {
        return oracle_text;
    }

    public void setOracle_text(String oracle_text) {
        this.oracle_text = oracle_text;
    }

    public String getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(String loyalty) {
        this.loyalty = loyalty;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Map<String, String> getLegalities() {
        return legalities;
    }

    public void setLegalities(Map<String, String> legalities) {
        this.legalities = legalities;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getCollector_number() {
        return collector_number;
    }

    public void setCollector_number(String collector_number) {
        this.collector_number = collector_number;
    }

    public String getPrints_search_uri() {
        return prints_search_uri;
    }

    public void setPrints_search_uri(String prints_search_uri) {
        this.prints_search_uri = prints_search_uri;
    }

    public boolean hasImageInCardFaces() {
        return (this.getCard_faces() != null
                && this.getCard_faces()
                .get(this.getFace_position())
                .getImage_uris() != null);
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSet_search_uri() {
        return set_search_uri;
    }

    public void setSet_search_uri(String set_search_uri) {
        this.set_search_uri = set_search_uri;
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

    @NonNull
    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                '}';
    }
}
