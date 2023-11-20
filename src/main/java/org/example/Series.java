package org.example;

import java.util.ArrayList;

public class Series extends Media {
    private String years;
    private ArrayList<String> seasonsAndEpisodes;

    public Series(String title, double rating, ArrayList<String> categories, String years, ArrayList<String> seasonsAndEpisodes) {
        super(title, rating, categories);
        this.years = years;
        this.seasonsAndEpisodes = seasonsAndEpisodes;
    }

}
