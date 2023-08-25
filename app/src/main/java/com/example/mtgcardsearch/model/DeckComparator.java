package com.example.mtgcardsearch.model;

import java.util.Comparator;

public class DeckComparator implements Comparator<Deck> {

    private String comparator;

    public DeckComparator(String comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(Deck d1, Deck d2) {
        if (comparator.equals("name"))
            return d1.getName().compareTo(d2.getName());
        if (comparator.equals("format"))
            return d1.getFormat().compareTo(d2.getFormat());
        return d1.getCreated_on().compareTo(d2.getCreated_on());
    }
}
