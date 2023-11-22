package org.example;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileIO io = new FileIO();
        TextUI ui = new TextUI();
        Chill ch = new Chill();
        List<Media> movies = io.loadMovies();
        List<Media> series = io.loadSeries();
        List<Media> all = io.loadList();
        User currentUser;
        List<User> users = new ArrayList<>();

        //Start af programmet, sæt en bruger
        String input = ui.getInput("Vil du:\n 1) Oprette en ny bruger \n 2) Logge ind på en eksisterende bruger");
        if(input.equals("1")){
            String username = ui.getInput("Choose a username: ");
            String password = ui.getInput("Choose a password: ");
            int age = Integer.parseInt(ui.getInput("Write your age: "));
            List<String> empty = new ArrayList<>();
            io.createUser(username,password,age);
            ui.displayMessage("User has been created,please loin now");
            currentUser = io.login(ui.getInput("Write your username"),ui.getInput("Write your password"));
        }else if(input.equals("2")){
            currentUser = io.login(ui.getInput("Write your username"),ui.getInput("Write your password"));
        }

        //Giv brugeren valgmuligheder
        input = ui.getInput("Hvad vil du nu? \n 1) Søge efter en film/serie \n 2) Se alle film/serier i en bestemt kategori \n 3) Søge efter film/serier med en bestemt rating eller over \n 4) Se Shrek");
        if(input.equals("1")){
            input = ui.getInput("Indtast søgeord: ");
            ch.searchByName(input,all);
        }else if(input.equals("2")){
            input = ui.getInput("Vælg kategori");
            ui.displayCategories(io.getCategories());
        }



    }

}