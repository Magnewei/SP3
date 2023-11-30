package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBConnector implements IO{
    // database URL
    static final String DB_URL = "jdbc:mysql://localhost/world";

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
        List<Media> series = new ArrayList<>();
        String selectQuery = "SELECT name, yearsRunning, genre, rating, seasonsAndEpisodes FROM streaming.series";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
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
    public List<String> loadUserMedia(User u) throws FileNotFoundException {
        return null;
    }



    @Override
    public List<Media> loadList() {
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
        return null;
    }
    /*
    public void createDB(){
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            //STEP 1: Register JDBC driver
            //Class.forName("com.mysql.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "CREATE DATABASE IF NOT EXISTS ChillFlix DEFAULT CHARSET = utf8mb4;use ChillFlix;CREATE TABLE IF NOT EXISTS movie (movieID INT NOT NULL AUTO_INCREMENT,genre VARCHAR(200) NULL DEFAULT 'All',name VARCHAR(70) NOT NULL,year INT NOT NULL,rating DOUBLE NULL,PRIMARY KEY (movieID),UNIQUE INDEX name_UNIQUE (name ASC) VISIBLE);";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
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
     */
}
