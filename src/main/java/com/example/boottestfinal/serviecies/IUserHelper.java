package com.example.boottestfinal.serviecies;

import com.example.boottestfinal.entities.User;

import java.util.List;

public interface IUserHelper {
    void addUser(User user);
    List<User> findAllUsers();
    void deleteId(int id);
    User getUser(int id);
}
