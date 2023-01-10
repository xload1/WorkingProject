package com.example.boottestfinal.serviecies;

import com.example.boottestfinal.entities.User;
import com.example.boottestfinal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserHelper implements IUserHelper {
    private UserRepository userRepository;
    @Autowired
    public UserHelper(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }
    @Override
    public void addUser(User user){
        userRepository.save(user);
    }
    @Override
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
    @Override
    public void deleteId(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUser(int id) {
        Optional<User> user =  userRepository.findById(id);
        return user.orElse(null);
    }
}
