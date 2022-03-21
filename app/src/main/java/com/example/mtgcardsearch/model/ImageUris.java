package com.example.mtgcardsearch.model;

public class ImageUris {
    public String small;
    public String normal;
    public String large;
    public String png;
    public String art_crop;
    public String border_crop;

    public ImageUris(String small, String normal, String large, String png, String art_crop, String border_crop) {
        this.small = small;
        this.normal = normal;
        this.large = large;
        this.png = png;
        this.art_crop = art_crop;
        this.border_crop = border_crop;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public String getArt_crop() {
        return art_crop;
    }

    public void setArt_crop(String art_crop) {
        this.art_crop = art_crop;
    }

    public String getBorder_crop() {
        return border_crop;
    }

    public void setBorder_crop(String border_crop) {
        this.border_crop = border_crop;
    }
}
