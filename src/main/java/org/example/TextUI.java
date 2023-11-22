package org.example;

import java.util.ArrayList;
import java.util.List;
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


    String displayMedia(List<Media> media) {
        for (int i = 0; i < media.size(); i++) {

            System.out.println(media.get(i).getTitle());
        }
        return null;
    }

    public void displayCategories(List<String> categories){
        for (int i = 0; i < categories.size(); i++) {

            System.out.println(categories.get(i));
        }
    }
}