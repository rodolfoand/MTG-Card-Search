package com.example.mtgcardsearch.model;

import com.google.gson.annotations.SerializedName;

public class Legalities {
    @SerializedName("standard")
    public String standard;
    @SerializedName("future")
    public String future;
    @SerializedName("historic")
    public String historic;
    @SerializedName("gladiator")
    public String gladiator;
    @SerializedName("pioneer")
    public String pioneer;
    @SerializedName("modern")
    public String modern;
    @SerializedName("legacy")
    public String legacy;
    @SerializedName("pauper")
    public String pauper;
    @SerializedName("vintage")
    public String vintage;
    @SerializedName("penny")
    public String penny;
    @SerializedName("commander")
    public String commander;
    @SerializedName("brawl")
    public String brawl;
    @SerializedName("historicbrawl")
    public String historicbrawl;
    @SerializedName("alchemy")
    public String alchemy;
    @SerializedName("paupercommander")
    public String paupercommander;
    @SerializedName("duel")
    public String duel;
    @SerializedName("oldschool")
    public String oldschool;
    @SerializedName("premodern")
    public String premodern;

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getFuture() {
        return future;
    }

    public void setFuture(String future) {
        this.future = future;
    }

    public String getHistoric() {
        return historic;
    }

    public void setHistoric(String historic) {
        this.historic = historic;
    }

    public String getGladiator() {
        return gladiator;
    }

    public void setGladiator(String gladiator) {
        this.gladiator = gladiator;
    }

    public String getPioneer() {
        return pioneer;
    }

    public void setPioneer(String pioneer) {
        this.pioneer = pioneer;
    }

    public String getModern() {
        return modern;
    }

    public void setModern(String modern) {
        this.modern = modern;
    }

    public String getLegacy() {
        return legacy;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public String getPauper() {
        return pauper;
    }

    public void setPauper(String pauper) {
        this.pauper = pauper;
    }

    public String getVintage() {
        return vintage;
    }

    public void setVintage(String vintage) {
        this.vintage = vintage;
    }

    public String getPenny() {
        return penny;
    }

    public void setPenny(String penny) {
        this.penny = penny;
    }

    public String getCommander() {
        return commander;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public String getBrawl() {
        return brawl;
    }

    public void setBrawl(String brawl) {
        this.brawl = brawl;
    }

    public String getHistoricbrawl() {
        return historicbrawl;
    }

    public void setHistoricbrawl(String historicbrawl) {
        this.historicbrawl = historicbrawl;
    }

    public String getAlchemy() {
        return alchemy;
    }

    public void setAlchemy(String alchemy) {
        this.alchemy = alchemy;
    }

    public String getPaupercommander() {
        return paupercommander;
    }

    public void setPaupercommander(String paupercommander) {
        this.paupercommander = paupercommander;
    }

    public String getDuel() {
        return duel;
    }

    public void setDuel(String duel) {
        this.duel = duel;
    }

    public String getOldschool() {
        return oldschool;
    }

    public void setOldschool(String oldschool) {
        this.oldschool = oldschool;
    }

    public String getPremodern() {
        return premodern;
    }

    public void setPremodern(String premodern) {
        this.premodern = premodern;
    }
}
