package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
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
            List<String> empty = new ArrayList<>();
            io.createUser(username,password,0);
            ui.displayMessage("User has been created,please login now");
            currentUser = io.login(ui.getInput("Write your username"),ui.getInput("Write your password"));
        }else if(input.equals("2")){
            currentUser = io.login(ui.getInput("Write your username"),ui.getInput("Write your password"));
        }else{
            ui.displayMessage("You need a user to use this service!");
            currentUser = null;
        }

        while(currentUser != null){
            //Giv brugeren valgmuligheder
            input = ui.getInput("Hvad vil du nu? \n 1) Søge efter en film/serie \n 2) Se alle film/serier i en bestemt kategori \n 3) Søge efter film/serier med en bestemt rating eller over \n 4) Se din liste over gemte film og serier \n 5) Se din liste over de film og serier du allerede har set \n 6) Se Shrek \n 7) afslutte programmet");
            Media currentMedia;
            switch (input) {
                case "1" -> {
                    input = ui.getInput("Indtast søgeord: ");
                    List<Media> list = ch.searchByName(input, all);
                    while(list.isEmpty()){
                        input = ui.getInput("That search didn't yield any results, search for something else");
                        list = ch.searchByName(input, all);
                    }
                    chooseMedia(ui, ch, currentUser, list);
                }
                case "2" -> {
                    ui.displayCategories(io.getCategories());
                    input = ui.getInput("Vælg kategori");
                    List<Media> categorised = ch.searchByCategory(input, all);
                    chooseMedia(ui, ch, currentUser, categorised);
                }
                case "3" -> {
                    String rating = ui.getInput("Hvilken rating skal filmen mindst have på IMDB?");
                    List<Media> list = ch.searchByRating(Double.parseDouble(rating), all);
                    while(list.isEmpty()){
                        rating = ui.getInput("No movies or series have this rating or above, lower your excpectations a bit");
                        list = ch.searchByRating(Double.parseDouble(rating), all);
                    }
                    chooseMedia(ui,ch,currentUser,list);
                }
                case "4" -> {
                    ui.displayMessage(currentUser.getSavedMedia().toString());
                }
                case "5" -> {
                    ui.displayMessage(currentUser.getWatchedMedia().toString());
                }
                case "6" -> new Shrek();
                case "7" -> {
                    io.saveMediaList(currentUser);
                    currentUser = null;
                }
                default -> ui.displayMessage("You have to pick a number, corresponding to one of the options");
            }

        }





    }

    private static void chooseMedia(TextUI ui, Chill ch, User currentUser, List<Media> sorted) {
        String input;
        Media currentMedia;
        ui.displayMedia(sorted);
        input = ui.getInput("Vælg en film eller serie:");
        for (Media media : sorted) {
            if (media.getTitle().equalsIgnoreCase(input)) {
                currentMedia = media;
                if(currentUser.getSavedMedia().contains(currentMedia.getTitle())) {
                    input = ui.getInput("Hvad vil du foretage dig? \n 1) Se denne film/serie \n 2) Fjerne denne film/serie fra din liste");
                    if(input.equals("1")){
                        ch.watchMedia(currentUser,currentMedia);
                    }else if (input.equals("2")){
                        ch.removeMedia(currentUser,currentMedia);
                    }
                }else{
                    input = ui.getInput("Hvad vil du foretage dig? \n 1) Se denne film/serie \n 2) Gemme denne film/serie på din liste");
                    if(input.equals("1")){
                        ch.watchMedia(currentUser,currentMedia);
                    } else if (input.equals("2")) {
                        ch.saveMedia(currentUser,currentMedia);
                    }
                }
            }

        }
    }

}