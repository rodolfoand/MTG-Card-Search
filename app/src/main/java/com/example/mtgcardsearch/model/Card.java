package com.example.mtgcardsearch.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Entity(tableName = "card_table")
public class Card {

    @Ignore
    @SerializedName("name")
    private String name;

    @Ignore
    @SerializedName("layout")
    private String layout;

    @Ignore
    @SerializedName("image_uris")
    private ImageUris image_uris;

    @Ignore
    private int face_position;

    @Ignore
    private String image_url;

    @Ignore
    @SerializedName("card_faces")
    private ArrayList<CardFace> card_faces;

    @Ignore
    @SerializedName("uri")
    private String uri;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String id;

    @Ignore
    @SerializedName("type_line")
    private String type_line;

    @Ignore
    @SerializedName("oracle_text")
    private String oracle_text;

    @Ignore
    @SerializedName("loyalty")
    private String loyalty;

    @Ignore
    @SerializedName("artist")
    private String artist;

    @Ignore
    @SerializedName("legalities")
    private Map<String, String> legalities;

    @Ignore
    @SerializedName("set_name")
    private String set_name;

    @Ignore
    @SerializedName("set")
    private String set;

    @Ignore
    @SerializedName("collector_number")
    private String collector_number;

    @Ignore
    @SerializedName("rarity")
    private String rarity;

    @Ignore
    @SerializedName("lang")
    private String lang;

    @Ignore
    @SerializedName("set_search_uri")
    private String set_search_uri;

    @Ignore
    @SerializedName("prints_search_uri")
    private String prints_search_uri;

    @Ignore
    @SerializedName("printed_name")
    private String printed_name;

    @Ignore
    @SerializedName("printed_type_line")
    private String printed_type_line;

    @Ignore
    @SerializedName("printed_text")
    private String printed_text;

    @Ignore
    @SerializedName("oracle_id")
    private String oracle_id;

    @Ignore
    @SerializedName("power")
    private String power;

    @Ignore
    @SerializedName("toughness")
    private String toughness;

    @Ignore
    @SerializedName("flavor_text")
    private String flavor_text;

    @Ignore
    private boolean whish;




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

    public String getPrinted_name() {
        return printed_name;
    }

    public void setPrinted_name(String printed_name) {
        this.printed_name = printed_name;
    }

    public String getPrinted_type_line() {
        return printed_type_line;
    }

    public void setPrinted_type_line(String printed_type_line) {
        this.printed_type_line = printed_type_line;
    }

    public String getPrinted_text() {
        return printed_text;
    }

    public void setPrinted_text(String printed_text) {
        this.printed_text = printed_text;
    }

    public String getOracle_id() {
        return oracle_id;
    }

    public void setOracle_id(String oracle_id) {
        this.oracle_id = oracle_id;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getFlavor_text() {
        return flavor_text;
    }

    public void setFlavor_text(String flavor_text) {
        this.flavor_text = flavor_text;
    }

    public boolean isWhish() {
        return whish;
    }

    public void setWhish(boolean whish) {
        this.whish = whish;
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
