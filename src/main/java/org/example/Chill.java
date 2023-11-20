package org.example;

import java.util.ArrayList;
import java.util.List;

public class Chill {

    private FileIO io = new FileIO();
    private TextUI ui = new TextUI();
    private User currentUser;
    public List<Movie> movieList = io.loadList();
    public List<Series> seriesList = io.loadList();

    //Saves the selected media to the users list of saved media.
    public void saveMedia(User u,Media m){
        u.savedMedia.add(m);
    }

    //"Plays" the selected media.
    public void watchMedia(User u,Media m){
        ui.displayMessage("You're now watching: " + m.getTitle());
        u.watchedMedia.add(m);
    }

    //Removes the selected media from the users list of saved media.
    public void removeMedia(User u, Media m){
        u.savedMedia.remove(m);
        ui.displayMessage(m.getTitle() + " has been removed from your saved list.");
    }

    //Searches for a query and returns a list of media where the title contains the query
    private List<Media> searchByName(String query,List<Media> list){
        List<Media> searchedList = new ArrayList<>();
        for(Media m:list){
            if(m.getTitle().contains(query)){
                searchedList.add(m);
            }
        }
        return searchedList;
    }

}
