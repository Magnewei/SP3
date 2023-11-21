package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private int age;
    private List<String> userWatchedMedia = new ArrayList<>();
    public List<Media> savedMedia = new ArrayList<>();
    public List<Media> watchedMedia = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getUserWatchedMedia() {
        return userWatchedMedia;
    }

    public User(String username, String password, int age, List<String> userWatchedMedia) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.userWatchedMedia = userWatchedMedia;
    }
}
