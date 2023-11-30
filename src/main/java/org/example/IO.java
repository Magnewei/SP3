package org.example;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface IO {
    public List<Media> loadSeries() throws SQLException;
    public List<Media> loadMovies() throws SQLException;
   // public List<User> loadUsers();
    public List<String> loadUserMedia(User u) throws FileNotFoundException;
    public List<Media> loadList() throws SQLException;
    public User login(String username, String password) throws FileNotFoundException;

    public List<String> getCategories() throws SQLException;
}



