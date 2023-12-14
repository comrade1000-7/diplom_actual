package com.example.diplom.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    List<File> userFiles;
}
