package ru.abtank.persist.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class User {

    private Integer id;
    @NotBlank //валидация
    private String login;
    @NotBlank //валидация
    private String password;
    private String matchingPassword;
    @Email //валидация
    @NotBlank
    private String email;

    public User(Integer id, String login, String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

//    public User(){}

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", matchingPassword='" + matchingPassword + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
