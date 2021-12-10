package com.example.evoting.model;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String embg;
    private boolean isAdminOrUser;
    private boolean finishVoting;
    private boolean isLogged;

    public User() {}

    public User(String firstName, String lastName, String email, String password, String confirmPassword, String embg, boolean isAdminOrUser, boolean finishVoting, boolean isLogged) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.embg = embg;
        this.isAdminOrUser = isAdminOrUser;
        this.finishVoting = finishVoting;
        this.isLogged = isLogged;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdminOrUser() {
        return isAdminOrUser;
    }

    public boolean isFinishVoting() {
        return finishVoting;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmbg() {
        return embg;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return isLogged;
    }

    public void setAdminOrUser(boolean adminOrUser) {
        isAdminOrUser = adminOrUser;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setEmbg(String embg) {
        this.embg = embg;
    }

    public void setFinishVoting(boolean finishVoting) {
        this.finishVoting = finishVoting;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLogged = loggedIn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
