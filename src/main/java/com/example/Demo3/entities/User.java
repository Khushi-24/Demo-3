package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "User")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String userEmail;

    @Column
    private String userName;

    @Column
    private String userPassword;

    @Column
    private String userRole;

    @OneToOne(mappedBy = "user")
    private Society society;
}
