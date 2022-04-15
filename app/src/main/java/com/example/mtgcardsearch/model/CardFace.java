package com.example.mtgcardsearch.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "cardface_table",
        foreignKeys = @ForeignKey(entity = Card.class,
                parentColumns = "id",
                childColumns = "card_id", onDelete = CASCADE))
public class CardFace {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cardface_id")
    private int cardface_id;

    @ColumnInfo(name = "card_id")
    private String card_id;

    @Ignore
    @SerializedName("name")
    private String name;

    @Embedded
    @SerializedName("image_uris")
    private ImageUris image_uris;

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

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public int getCardface_id() {
        return cardface_id;
    }

    public void setCardface_id(int cardface_id) {
        this.cardface_id = cardface_id;
    }
}
