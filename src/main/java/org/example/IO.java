package org.example;

import java.util.List;

public interface IO {
    List loadList();
    public String saveCredentials(User u);
    void createUser(String username, String password);
    void login(String username, String password);

}




