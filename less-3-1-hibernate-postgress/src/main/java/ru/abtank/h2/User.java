package ru.abtank.h2;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public User() {
    }

    public void print() {
        System.out.println("User id = " + id + "; name = " + name);
    }
}
