package org.example;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        FileIO io = new FileIO();
        List<User> s = new ArrayList<>();
        s = io.loadUsers();
        System.out.println(s.get(0).getUsername());
        System.out.println(io.loadSeries().get(2).getCategories());
    }
}