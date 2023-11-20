package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private int age;
    public List<Media> savedMedia = new ArrayList<>();
    public List<Media> watchedMedia = new ArrayList<>();



    public User(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }
}
