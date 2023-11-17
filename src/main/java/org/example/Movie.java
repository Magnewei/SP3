package org.example;

public class Movie extends Media {
   private int year;

    public Movie(String title, double rating, String categories, int year) {
        super(title, rating, categories);
        this.year = year;
    }
}
