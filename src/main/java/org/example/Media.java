package org.example;

public abstract class Media {
    private String title;
    private double rating;
    private String categories;

    public Media(String title, double rating, String categories) {
        this.title = title;
        this.rating = rating;
        this.categories = categories;
    }
}
