package com.example.boottestfinal.enities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.core.support.DefaultCrudMethods;

public interface UserRepository extends JpaRepository<User, Integer> {
}
