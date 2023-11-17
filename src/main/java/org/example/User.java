package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    public List<Media> savedMedia = new ArrayList<>();
    public List<Media> watchedMedia = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
