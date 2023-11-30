package org.example;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException, SQLException {

        FileIO io = new FileIO();
        List<User> users = new ArrayList<>();
        users = io.loadUsers();
        DBConnector db = new DBConnector();
        //Tester CSV loading
        db.loadFromCSV();
        //Tester User creation
        db.createUser("anton","12345");
        //Tester returnering af ArrayList
        List<Media> movies = db.loadMovies();
        System.out.println(movies.get(0).getTitle());
        List<Media> series = db.loadSeries();
        System.out.println(series.get(0).getTitle());
    }
}