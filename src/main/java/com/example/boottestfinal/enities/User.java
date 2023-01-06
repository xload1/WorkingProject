package com.example.boottestfinal.enities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "usertable")
public class User {
    @Id
    @SequenceGenerator(name = "userSequence", sequenceName = "userSequence", allocationSize = 1)
            @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
    int id;
    int age;
    String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
