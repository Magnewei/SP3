package org.example;

import java.io.FileNotFoundException;
import java.util.List;

public interface IO {
    public List<Media> loadSeries();
    public List<Media> loadMovies();
   // public List<User> loadUsers();
    public List<String> loadUserMedia(User u) throws FileNotFoundException;
    public List<Media> loadList();
    public void createUser(String username, String password, int age);
    public User login(String username, String password) throws FileNotFoundException;
}



