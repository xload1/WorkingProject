package com.example.boottestfinal.serviecies;

import com.example.boottestfinal.UserToDoRepository;
import com.example.boottestfinal.entities.UserToDoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserToDoService implements IUserToDoService{
    private UserToDoRepository userToDoRepository;
    @Autowired
    public void setUserToDoRepository(UserToDoRepository userToDoRepository) {
        this.userToDoRepository = userToDoRepository;
    }

    @Override
    public String checkPassword(String login, String password) {
        if(userToDoRepository.findById(login).isPresent()){
            if(userToDoRepository.findById(login).get().getPassword().equals(password))
                return "success";
            else return "wrong password";
        } else return "does not exist";
    }

    @Override
    public boolean checkPresence(String login) {
        return userToDoRepository.findById(login).isPresent();
    }

    @Override
    public void save(String login, String password) {
        userToDoRepository.save(new UserToDoList(login, password));
    }

    @Override
    public UserToDoList findById(String login) {
       if( userToDoRepository.findById(login).isPresent()) return userToDoRepository.findById(login).get();
       else return null;
    }

    @Override
    public void updateUser(UserToDoList userToDoList) {
        userToDoRepository.save(userToDoList);
    }

    @Override
    public String getTodos(String login) {
        if(userToDoRepository.findById(login).isPresent())
          return userToDoRepository.findById(login).get().getTodos();
        else return null;
    }
}
