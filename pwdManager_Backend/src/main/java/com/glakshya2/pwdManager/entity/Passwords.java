package com.glakshya2.pwdManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "passwords")
@Data
public class Passwords {

    @Id
    @Column(name="passId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int passId;

    @Column(name="website")
    private String website;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;
}
