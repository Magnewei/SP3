package org.example;

import java.util.ArrayList;
import java.util.List;

public class Chill {
    private final FileIO io = new FileIO();
    private final TextUI ui = new TextUI();
    private User currentUser;
    public List<Media> movieList = io.loadMovies();
    public List<Media> seriesList = io.loadSeries();

    //Saves the selected media to the users list of saved media.
    public void saveMedia(User u,Media m){
        u.savedMedia.add(m);
        ui.displayMessage(m.getTitle() + " has been added to your saved list.");
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
    public List<Media> searchByName(String query,List<Media> list){
        List<Media> searchedList = new ArrayList<>();
        for(Media m:list){
            if(m.getTitle().contains(query)){
                searchedList.add(m);
            }
        }
        return searchedList;
    }

    //Makes a list of all the media within the given category
    public List<Media> searchByCategory(String category,List<Media> list){
        List<Media> searchedList = new ArrayList<>();
        for(Media m:list){
            if(m.getCategories().contains(category)){
                searchedList.add(m);
            }
        }
        return searchedList;
    }

    //Returns a list of all the media with a higher rating than the given float.
    public List<Media> searchByRating(double rating,List<Media> list){
        List<Media> searchedList = new ArrayList<>();
        for(Media m:list){
            if(m.getRating()>=rating){
                searchedList.add(m);
            }
        }
        return searchedList;
    }

}
