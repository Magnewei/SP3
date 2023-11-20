package org.example;

import java.util.List;

public interface IO {
    public List<String> loadList(List<String> mediaList);
    public String saveCredentials(User u);
    void createUser(String username, String password, int age);
    void login(String username);
}



