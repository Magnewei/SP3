package org.example;

import java.util.ArrayList;

public class Movie extends Media {
   private int year;

    public Movie(String title, double rating, ArrayList<String> categories, int year) {
        super(title, rating, categories);
        this.year = year;
    }
}
