package com.example.boottestfinal;

import com.example.boottestfinal.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
}
