package com.example.boottestfinal.serviecies;

import com.example.boottestfinal.UserToDoRepository;
import com.example.boottestfinal.entities.UserToDoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodosService {

    private UserToDoRepository userToDoRepository;
    @Autowired
    public void setUserToDoRepository(UserToDoRepository userToDoRepository) {
        this.userToDoRepository = userToDoRepository;
    }
    public List<Pair<String, String>> getTodosList(String login){
        UserToDoList user;
        if(userToDoRepository.findById(login).isPresent()) {
            user = userToDoRepository.findById(login).get();
        } else return null;
        String[] todosList = user.getTodos().split("%s%");
        List<Pair<String, String>> pairList= new ArrayList<>();
        for(String todo : todosList){
            if(todo.split("%d%").length==2) {
                pairList.add(Pair.of(todo.split("%d%")[0], todo.split("%d%")[1]));
            }else {
                pairList.add(Pair.of(todo.split("%d%")[0], " "));
            }
        }
        return pairList;
    }
}
