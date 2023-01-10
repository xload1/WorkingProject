package com.example.boottestfinal.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "usertable")
public class User implements Serializable {
    @Id
//    @SequenceGenerator(name = "userSequence", sequenceName = "userSequence", allocationSize = 1)
//            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    int age;
    String name;

    @Override
    public String toString() {
        return "User " +
                "id=" + id +
                ", age=" + age +
                ", name=" + name;
    }

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
