package org.example;

import java.util.ArrayList;
import java.util.List;

public class Chill {

    private FileIO io = new FileIO();
    private TextUI ui = new TextUI();
    private User user1 = new User("Anton","1234",24);
    public List<Movie> movieList = new ArrayList<>();
    public List<Series> seriesList = new ArrayList<>();

    public void saveMedia(User u,Media m){
        u.savedMedia.add(m);
    }

    public void watchMedia(Media m){
        displayMessage("You're now watching: " + m.getTitle());
    }

    public void removeMedia(User u, Media m){
        u.savedMedia.remove(m);
        displayMessage(m.getTitle() + " has been removed from your saved list.");
    }

    private void search(String query){

    }

}
