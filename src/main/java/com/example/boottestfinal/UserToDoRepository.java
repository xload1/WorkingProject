package com.example.boottestfinal;

import com.example.boottestfinal.entities.UserToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserToDoRepository extends JpaRepository<UserToDoList, String> {
}
