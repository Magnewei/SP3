package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO implements IO {
    List<User> users = new ArrayList<User>();
    File userInfo = new File("txt/userSave.txt");

    // Loads list of movies / series, depending on user choice.


    public List<Media> loadMovies() {
        List<Media> mediaList = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        try {
            File movies = new File("txt/100bedstefilm.txt");
            Scanner scanner = new Scanner(movies);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";"); // Splits the text line by semicolon.
                String title = parts[0];
                int year = Integer.parseInt(parts[1].trim());
                String genres = parts[2].trim();
                double rating = Double.parseDouble(parts[3].replace(",", ".").trim());

                // Splits the genres into text strings. E.g "; drama, crime;" will be written to seperate variables.
                String[] genresArray = genres.split(",");    // Splits the genres in the line at comma.
                for (String s : genresArray) {
                    categories.add(genres.trim());
                }

                mediaList.add(new Movie(title, rating, (ArrayList<String>) categories, year));

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            throw new RuntimeException(e);
        }

        return mediaList;
    }


    // Returns a list of series.
    public List<Media> loadSeries() {
        List<Media> mediaList = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<String> seasonsAndEpisodes = new ArrayList<>();

        try {
            File series = new File("txt/100bedsteserier.txt");
            Scanner scanner = new Scanner(series);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("; "); // Splits the text line by semicolon or "-".

                String title = parts[0].trim();
                String years = parts[1].trim();

                // Changes "," to "." to comply with double variable.
                double rating = Double.parseDouble(parts[3].replace(",", ".").trim());

                // Splits the genres into text strings. E.g "; drama, crime;" will be written to seperate variables.
                String genres = parts[2].trim();
                String[] genresArray = genres.split(","); // Splits the genres in the line at comma.
                for (String s : genresArray) {
                    categories.add(genres.trim());
                }

                // Splits the seasons and episodes into separate strings.
                String seasons = parts[4].trim();
                String[] seasonsArray = genres.split(",");
                for (String s : seasonsArray) {
                    seasonsAndEpisodes.add(seasons.trim());
                }

                mediaList.add(new Series(title, rating, (ArrayList<String>) categories, years, (ArrayList<String>) seasonsAndEpisodes));

            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found." + e.getMessage());
            throw new RuntimeException(e);
        }
        return mediaList;
    }

    // Returns a combined list of movies and series.
    public List<Media> loadList() {
        List<Media> moviesAndSeries = new ArrayList<>();
        moviesAndSeries.addAll(loadMovies());
        moviesAndSeries.addAll(loadSeries());
        return moviesAndSeries;
    }

    // Overwrites user credentials in userSave.txt.
    @Override
    public String saveCredentials(User u) {

        // What's the goal of this method?
        // Saving new information? Watched media?

        return null;
    }

    // Creates user object and calls saveCredentials() to write in userSave.txt.
    @Override
    public void createUser(String username, String password, int age) {

        try {
            FileWriter writer = new FileWriter(userInfo, true);
            writer.write(username + " ; " + password + " ; " + age);
            writer.close();
            System.out.println("Username and password successfully written to file.");

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
            users.add(new User(username, password, age));
        }
    }

    // Looks up user in .txt file.
    @Override
    public void login(String username, String password) {

        try {
            Scanner scanner = new Scanner(userInfo);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.contains("Username:" + username + ", Password: " + password)) {

                    // Missing functionality. What to do if line.contains == true?

                    scanner.close();
                }
            }
            scanner.close();

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
