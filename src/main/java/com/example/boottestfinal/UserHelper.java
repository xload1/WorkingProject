package com.example.boottestfinal;

import com.example.boottestfinal.enities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.boottestfinal.enities.User;

import java.util.List;

@Component
public class UserHelper {
    UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void addUser(User user){
        userRepository.save(user);
    }
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
