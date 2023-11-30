package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DBConnector implements IO{
    // database URL
    static final String DB_URL = "jdbc:mysql://localhost/streaming";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "1o9B0lu&FCp%";

    static final String moviePath = "txtcsv/movie.csv";
    static final String seriesPath = "txtcsv/series.csv";



    //Lægger data ind i databasen
    public void loadFromCSV() {

        Connection conn = null;
        PreparedStatement stmt = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             BufferedReader br = new BufferedReader(new FileReader(moviePath))) {

            // Check if the table is empty
            String countQuery = "SELECT COUNT(*) FROM streaming.movie";
            ResultSet resultSet = statement.executeQuery(countQuery);
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            if (rowCount == 0) {
                // Table is empty, proceed with the insertion
                String line;
                String insertQuery = "INSERT INTO streaming.movie (name, year, genre, rating) VALUES (?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        // Set parameters based on your CSV file columns and data types
                        preparedStatement.setString(1, values[0].trim());  // movie name
                        preparedStatement.setInt(2, Integer.parseInt(values[1].trim()));  // year of release
                        preparedStatement.setString(3, values[2].trim());  // genres
                        preparedStatement.setDouble(4, Double.parseDouble(values[3].trim()));  // rating

                        preparedStatement.executeUpdate();
                    }
                }

                System.out.println("Data loaded successfully!");
            } else {
                System.out.println("Table is not empty. Data insertion skipped.");
            }

        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             BufferedReader br = new BufferedReader(new FileReader(seriesPath))) {

            // Check if the table is empty
            String countQuery = "SELECT COUNT(*) FROM streaming.series";
            ResultSet resultSet = statement.executeQuery(countQuery);
            resultSet.next();
            int rowCount = resultSet.getInt(1);

            if (rowCount == 0) {
                // Table is empty, proceed with the insertion
                String line;
                String insertQuery = "INSERT INTO streaming.series (name, yearsRunning, genre, rating, seasonsAndEpisodes) VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        // Set parameters based on your CSV file columns and data types
                        preparedStatement.setString(1, values[0].trim());  // movie name
                        preparedStatement.setString(2, values[1].trim());  // years running
                        preparedStatement.setString(3, values[2].trim());  // genres
                        preparedStatement.setDouble(4, Double.parseDouble(values[3].trim()));  // rating
                        preparedStatement.setString(5,values[4].trim());

                        preparedStatement.executeUpdate();
                    }
                }

                System.out.println("Data loaded successfully!");
            } else {
                System.out.println("Table is not empty. Data insertion skipped.");
            }

        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try



    }
    @Override
    public List<Media> loadSeries() throws SQLException {
        //Klargør liste og query
        List<Media> series = new ArrayList<>();
        String selectQuery = "SELECT name, yearsRunning, genre, rating, seasonsAndEpisodes FROM streaming.series";
        //Opretter forbindelse
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            //Så længe der er flere rækker i databasen
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String years = resultSet.getString("yearsRunning");
                double rating = resultSet.getDouble("rating");

                String seasonsAndEpisodesString = resultSet.getString("seasonsAndEpisodes");
                String[] parts0 = seasonsAndEpisodesString.split(",");
                ArrayList<String> seasonsAndEpisodes = new ArrayList<>(Arrays.asList(parts0));

                String categoryString = resultSet.getString("genre");
                String[] parts1 = categoryString.split(",");
                ArrayList<String> categories = new ArrayList<>(Arrays.asList(parts1));

                Series s = new Series(name,rating,categories,years,seasonsAndEpisodes);
                series.add(s);
            }
        }
        return series;
    }

    @Override
    public List<Media> loadMovies() throws SQLException {
        List<Media> movies = new ArrayList<>();
        String selectQuery = "SELECT name, year, genre, rating FROM streaming.movie";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int year = resultSet.getInt("year");
                double rating = resultSet.getDouble("rating");

                String categoryString = resultSet.getString("genre");
                String[] parts = categoryString.split(",");
                ArrayList<String> categories = new ArrayList<>(Arrays.asList(parts));

                Movie m = new Movie(name, rating, categories, year);
                movies.add(m);
            }
        }
        return movies;
    }

    @Override
    public List<Media> loadList() throws SQLException {
        List<Media> moviesAndSeries = new ArrayList<>();
        moviesAndSeries.addAll(loadMovies());
        moviesAndSeries.addAll(loadSeries());
        return moviesAndSeries;
    }

    public List<User> loadUsers() throws FileNotFoundException {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT username, password FROM streaming.user";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            //Så længe der er flere rækker i databasen
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                List<String> watchedMedia = new ArrayList<>();
                List<String> savedMedia = new ArrayList<>();

                // Get the current user's ID
                int userID;
                String getUserIdQuery = "SELECT userID FROM streaming.user WHERE username = ?";
                try (PreparedStatement getUserIdStatement = connection.prepareStatement(getUserIdQuery)) {
                    getUserIdStatement.setString(1, username);
                    ResultSet userIdResult = getUserIdStatement.executeQuery();

                    if (userIdResult.next()) {
                        userID = userIdResult.getInt("userID");
                    } else {
                        throw new RuntimeException("User not found");
                    }
                }

                String watchedMovieQuery = "SELECT name FROM movie JOIN watched_movie ON movie.movieID = watched_movie.movieID JOIN user ON user.userID = watched_movie.userID WHERE user.userID = ?;";
                try (PreparedStatement watchedStatement = connection.prepareStatement(watchedMovieQuery)) {
                    watchedStatement.setInt(1, userID);
                    ResultSet resultSet1 = watchedStatement.executeQuery();

                    while (resultSet1.next()) {
                        String movie = resultSet1.getString("name");
                        watchedMedia.add(movie);
                    }
                }

                String savedMovieQuery = "SELECT name FROM movie JOIN saved_movie ON movie.movieID = saved_movie.movieID JOIN user ON user.userID = saved_movie.userID WHERE user.userID = ?;";
                try (PreparedStatement savedStatement = connection.prepareStatement(savedMovieQuery)) {
                    savedStatement.setInt(1, userID);
                    ResultSet resultSet2 = savedStatement.executeQuery();

                    while (resultSet2.next()) {
                        String movie = resultSet2.getString("name");
                        savedMedia.add(movie);
                    }
                }

                String watchedSeriesQuery = "SELECT name FROM series JOIN watched_series ON series.seriesID = watched_series.seriesID JOIN user ON user.userID = watched_series.userID WHERE user.userID = ?;";
                try (PreparedStatement watchedSeriesStatement = connection.prepareStatement(watchedSeriesQuery)) {
                    watchedSeriesStatement.setInt(1, userID);
                    ResultSet resultSet3 = watchedSeriesStatement.executeQuery();

                    while (resultSet3.next()) {
                        String series = resultSet3.getString("name");
                        watchedMedia.add(series);
                    }
                }

                String savedSeriesQuery = "SELECT name FROM series JOIN saved_series ON series.seriesID = saved_series.seriesID JOIN user ON user.userID = saved_series.userID WHERE user.userID = ?;";
                try (PreparedStatement savedSeriesStatement = connection.prepareStatement(savedSeriesQuery)) {
                    savedSeriesStatement.setInt(1, userID);
                    ResultSet resultSet4 = savedSeriesStatement.executeQuery();

                    while (resultSet4.next()) {
                        String series = resultSet4.getString("name");
                        savedMedia.add(series);
                    }
                }
                users.add(new User(username,password,watchedMedia,savedMedia));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public List<String> loadUserMedia(User u) throws FileNotFoundException {
        return null;
    }

    public void createUser(String username, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()){
            String line;
            String insertQuery = "INSERT INTO streaming.user (username, password) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set parameters based on your CSV file columns and data types
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);

                preparedStatement.executeUpdate();

            }
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){

            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try

    }

    @Override
    public User login(String username, String password) throws FileNotFoundException {
        List<User> users = loadUsers();
        for(User user : users) {
            if (user.getUsername().equals(username)  && user.getPassword().equals(password)) {
                return user;
            }
        }
        System.out.println("No user with the username and/or password was found.");
        return null;
    }

    @Override
    public List<String> getCategories() throws SQLException {
        List<Media> medias = loadList();
        List<String> categories = new ArrayList<>();
        for (Media media : medias) {
            categories.addAll(media.getCategories());
        }
        Set<String> uniques = new HashSet<>(categories);
        return List.copyOf(uniques);
    }

    public void saveMediaList(User u){

    }

    public int getMediaID(String mediaType, String mediaTitle) throws SQLException {
        String query;
        switch (mediaType.toLowerCase()) {
            case "movie":
                query = "SELECT movieID FROM movie WHERE name = ?";
                break;
            case "series":
                query = "SELECT seriesID FROM series WHERE name = ?";
                break;
            default:
                throw new IllegalArgumentException("Invalid media type: " + mediaType);
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, mediaTitle);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);  // Assuming the ID is in the first column
            } else {
                throw new RuntimeException("Media not found: " + mediaTitle);
            }
        }
    }
}
