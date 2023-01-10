package com.example.boottestfinal.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user-todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserToDoList {
    @Id
    private String login;
    private String password;
    private String todos;

    public UserToDoList(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
