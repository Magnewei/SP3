package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileIO implements IO {


  //  public List<User> users = new ArrayList<>();
    private final File userInfo = new File("txt/userSave.txt");



    // Loads a list of movies from .txt and returns list of Movie-objects.
    public List<Media> loadMovies() {
        List<Media> mediaList = new ArrayList<>();


        try {
            File movies = new File("txt/100bedstefilm.txt");
            Scanner scanner = new Scanner(movies);

            while(scanner.hasNextLine()) {
                List<String> categories = new ArrayList<>();
                String line = scanner.nextLine();
                String[] parts = line.split(";"); // Splits the text line by semicolon.
                String title = parts[0];
                int year = Integer.parseInt(parts[1].trim());
                String genres = parts[2].trim();
                double rating = Double.parseDouble(parts[3].replace(",", ".").trim());

                // Splits the genres into text strings. E.g "; drama, crime;" will be written to seperate variables.
                String[] genresArray = genres.split(",");    // Splits the genres in the line at comma.

                for (String s : genresArray) {
                    categories.add(s.trim());
                }

                mediaList.add(new Movie(title, rating, (ArrayList<String>) categories, year));

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            throw new RuntimeException(e);
        }

        return mediaList;
    }


    // Loads a list of series from .txt and returns list of Series-objects.
    public List<Media> loadSeries() {
        List<Media> mediaList = new ArrayList<>();


        try {
            File series = new File("txt/100bedsteserier.txt");
            Scanner scanner = new Scanner(series);

            while(scanner.hasNextLine()) {
                List<String> categories = new ArrayList<>();
                List<String> seasonsAndEpisodes = new ArrayList<>();
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
                    categories.add(s.trim());
                }

                // Splits the seasons and episodes into separate strings.
                String seasons = parts[4].trim();
                String[] seasonsArray = seasons.split(",");
                for (String s : seasonsArray) {
                    seasonsAndEpisodes.add(s.trim());

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


    // Loads users into an ArrayList, for later in-memory crosschecking and verification of datatypes.
    public List<User> loadUsers() throws FileNotFoundException {
        List<User> users = new ArrayList<>();
        Scanner scanner = new Scanner(new File("txt/userSave.txt"));

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("; ");

            if (parts.length < 5) {
                System.out.println("Skipping malformed line: " + line);
                continue;
            }

            String name = parts[0].trim();
            String password = parts[1].trim();
            int age = Integer.parseInt(parts[2].trim());


            // Loads userWatchedMedia from text into ArrayList.
            List<String> userWatchedMedia = Arrays.stream(parts[3].trim().split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            // Loads userSavedMedia from text into ArrayList.
            List<String> userSavedMedia = Arrays.stream(parts[4].trim().split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());

            users.add(new User(name, password, age, userWatchedMedia, userSavedMedia));
        }


        return users;
    }


    // Writes savedMedia and watchedMedia lists from a passed user object to userSave.txt.
    public void saveMediaList(User u) throws IOException {
        File file = new File("txt/userSave.txt");

        List<String> fileContent = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("; ");

                // Looks for the users name and password, so the correct line is edited.
                if (parts.length >= 2 && parts[0].trim().equals(u.getUsername()) && parts[1].trim().equals(u.getPassword())) {

                    // Update the line with new watched and saved media lists.
                    String watchedMediaString = String.join(", ", u.getWatchedMedia());
                    String savedMediaString = String.join(", ", u.getSavedMedia());
                    line = parts[0] + "; " + parts[1] + "; " + parts[2] + "; " + watchedMediaString + "; " + savedMediaString + ";";
                }
                fileContent.add(line);
            }
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (String newLine : fileContent) {
                pw.println(newLine);
            }
        }
    }


    // Loads users into user-ArrayList.
    public List<String> loadUserMedia(User u) throws FileNotFoundException {
        List<User> users = loadUsers();
        for (User user : users) {
            if (u.equals(user)) {
                 return user.getSavedMedia();
            }
        }
        return null;
    }


    // Creates user object writes object variables into userSave.txt.
    public void createUser(String username, String password, int age) {

        try {
            FileWriter writer = new FileWriter("txt/userSave.txt", true);
            writer.write("\n" + username + "; " + password + "; " + age + "; Shrek" +  "; Shrek");
            writer.close();
            System.out.println("Username, password and age has been successfully written to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    // Looks up existing users according to username and password.
    // Returns user object if correct username and password are found.
    public User login(String username, String password) throws FileNotFoundException {
        List<User> users = loadUsers();
        for(User user : users) {
            if (user.getUsername().equals(username)  && user.getPassword().equals(password)) {
                return user;
            }
        }
        System.out.println("User not found.");
        return null;
    }

    public List<String> getCategories(){
        List<Media> medias = loadList();
        List<String> categories = new ArrayList<>();
        for (int i = 0; i < medias.size(); i++) {
            categories.addAll(medias.get(i).getCategories());
        }
        Set<String> uniques = new HashSet<>(categories);
        List<String> uCategories = List.copyOf(uniques);
        return uCategories;
    }

}
