package org.example;

import java.util.List;

public interface IO {
    public List<Media> loadSeries();
    public List<Media> loadMovies();
    public List<Media> loadList();
    public void createUser(String username, String password, int age);
    public void login(String username, String password);
}



