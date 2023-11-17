package org.example;

import java.util.ArrayList;

public class Series extends Media {
    private int startYear;
    private int endYear;
    private ArrayList<String> seasonsAndEpisodes;

    public Series(String title, double rating, String categories, int startYear, int endYear) {
        super(title, rating, categories);
        this.startYear = startYear;
        this.endYear = endYear;
    }
}
