package org.example;

import java.util.ArrayList;

public abstract class Media {
    private String title;
    private double rating;
    private ArrayList<String> categories;

    public Media(String title, double rating, ArrayList<String> categories) {
        this.title = title;
        this.rating = rating;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
