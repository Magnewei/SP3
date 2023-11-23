package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Randomizer {

    public static void movieOrSeries(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vil du prøve lykken med film eller serier?");
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("film")) {
            pickRandomMedia("txt/100bedstefilm.txt");
        } else if (response.equalsIgnoreCase("serier")) {
            pickRandomMedia("txt/100bedsteserier.txt");
        } else {
            System.out.println("Ugyldigt valg. Her er Shrek i stedet");
            //afspille shrek gif
        }
    }

    public static void pickRandomMedia(String fileName) {

        List<String> mediaList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                mediaList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!mediaList.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(mediaList.size());
            String randomMedia = mediaList.get(randomIndex);
            System.out.println("Tillykke! Du har valgt: " + randomMedia);
        } else {
            System.out.println("Her er Shrek i stedet");

        }
    }
}