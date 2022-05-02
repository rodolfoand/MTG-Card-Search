package com.example.mtgcardsearch.model;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {

    private String comparator;

    public CardComparator() {
        this.comparator = "";
    }

    public CardComparator(String comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(Card c1, Card c2) {
        if (comparator.equals("cmc"))
            return c1.getCmc().compareTo(c2.getCmc());
        if (comparator.equals("rarity")) {
            int c1Rar = (c1.getRarity().equals("common")) ? 1
                    : (c1.getRarity().equals("uncommon")) ? 2
                    : (c1.getRarity().equals("rare")) ? 3
                    : (c1.getRarity().equals("special")) ? 4
                    : (c1.getRarity().equals("mythic")) ? 5
                    : 6;
            int c2Rar = (c2.getRarity().equals("common")) ? 1
                    : (c2.getRarity().equals("uncommon")) ? 2
                    : (c2.getRarity().equals("rare")) ? 3
                    : (c2.getRarity().equals("special")) ? 4
                    : (c2.getRarity().equals("mythic")) ? 5
                    : 6;
            if (c1Rar == c2Rar) return 0;
            if (c1Rar > c2Rar) return 1;
            if (c1Rar < c2Rar) return -1;
        }
        if (comparator.equals("power")) {
            if (c1.getPower() == null) return -1;
            if (c2.getPower() == null) return 1;
            return c1.getPower().compareTo(c2.getPower());
        }
        if (comparator.equals("toughness")) {
            if (c1.getToughness() == null) return -1;
            if (c2.getToughness() == null) return 1;
            return c1.getToughness().compareTo(c2.getToughness());
        }
        if (comparator.equals("type")) {
            if (c1.getType_line().contains("Creature"))
                return -1;
            if (c2.getType_line().contains("Creature"))
                return 1;
            if (c1.getType_line().contains("Planeswalker"))
                return -1;
            if (c2.getType_line().contains("Planeswalker"))
                return 1;
            if (c1.getType_line().contains("Instant"))
                return -1;
            if (c2.getType_line().contains("Instant"))
                return 1;
            if (c1.getType_line().contains("Sorcery"))
                return -1;
            if (c2.getType_line().contains("Sorcery"))
                return 1;
            if (c1.getType_line().contains("Artifact"))
                return -1;
            if (c2.getType_line().contains("Artifact"))
                return 1;
            if (c1.getType_line().contains("Enchantment"))
                return -1;
            if (c2.getType_line().contains("Enchantment"))
                return 1;
            if (c1.getType_line().contains("Land"))
                return -1;
            if (c2.getType_line().contains("Land"))
                return 1;

        }

        return c1.getName().compareTo(c2.getName());
    }
}
