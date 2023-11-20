package org.example;

import java.io.File;
import org.example.IO;
import org.example.User;
import java.util.List;

public class FileIO implements IO {

    File series = new File("txt/100bedsteserier.txt");
    File movies = new File("txt/100bedstefilm.txt");
    File userInfo = new File("txt/userSave.txt");

    // Loads list of movies / series, depending on user choice.
    @Override
    public List<String> loadList(List<String> mediaList) {

        /*
        try {
            Scanner scanner = new Scanner();

            while(scanner.hasNextLine()) {

                //scanner.nextLine();
            }

        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        }


 */
        return null;
    }

    // Overwrites user credentials in userSave.txt.
    @Override
    public String saveCredentials(User u) {
        return null;
    }

    // Creates user object and calls saveCredentials() to write in userSave.txt.
    @Override
    public void createUser(String username, String password, int age) {
        new User(username, password, age);

    }

    // Looks up user in .txt file.
    @Override
    public void login(String username) {
        //scan userSave.txt.

        // If username exists > recursion login(String) again to find password.

    }
}
