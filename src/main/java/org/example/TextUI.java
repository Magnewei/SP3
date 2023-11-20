package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class TextUI {
    private Scanner scan = new Scanner(System.in);

    //shows a message and returns the user's input as a String
    public String getInput(String msg) {
        this.displayMessage(msg);
        return scan.nextLine();
    }


    public void displayMessage(String msg) {
        System.out.println(msg);
    }


    String displayWatchedMedia(ArrayList<Media> watchedMedia) {
        for (int i = 0; i < watchedMedia.size(); i++) {

            System.out.println(watchedMedia.get(i).getTitle());
        }
        return null;
    }

        String displaySavedMedia (ArrayList<Media> savedMedia) {
            for (int i = 0; i < savedMedia.size(); i++) {

                System.out.println(savedMedia.get(i).getTitle());
            }
            return null;
        }
}