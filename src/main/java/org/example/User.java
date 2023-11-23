package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    public List<String> savedMedia = new ArrayList<>();
    public List<String> watchedMedia = new ArrayList<>();
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getSavedMedia() {
        return savedMedia;
    }

    public List<String> getWatchedMedia() {
        return watchedMedia;
    }

    public User(String username, String password, int age, List<String> watchedMedia, List<String> savedMedia) {
        this.username = username;
        this.password = password;
        this.watchedMedia = watchedMedia;
        this.savedMedia = savedMedia;
    }
}
