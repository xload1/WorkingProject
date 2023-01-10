package com.example.boottestfinal.serviecies;

import com.example.boottestfinal.entities.UserToDoList;

public interface IUserToDoService{
    String checkPassword(String login, String password);
    boolean checkPresence(String login);
    void save(String login, String password);
    UserToDoList findById(String login);
    void updateUser(UserToDoList userToDoList);
    String getTodos(String login);
}
