package org.example;

import java.util.List;

public interface IO {
    public List<Media> loadSeries();
    public List<Media> loadMovies();

    public List<Media> loadList();
    public String saveCredentials(User u);
    void createUser(String username, String password, int age);
    void login(String username);
}



