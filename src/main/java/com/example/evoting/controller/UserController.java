package com.example.evoting.controller;

import com.example.evoting.model.User;
import com.example.evoting.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins =  "http://localhost:4200")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/users/create")
    void addUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @PutMapping("/users/logged-in")
    void updateLoggedInUser(@RequestBody String email) {
        User user = userRepository.findByEmail(email);
        user.setLoggedIn(true);
        userRepository.save(user);
    }

    @PutMapping("/users/logged-out")
    void updateLoggedOutUser(@RequestBody String email) {
        User user = userRepository.findByEmail(email);
        user.setLoggedIn(false);
        userRepository.save(user);
    }

    @GetMapping("/users/logged-in")
    public User getLoggedInUser() {
        return userRepository.findByIsLogged(true);
    }

    @PutMapping("/users/finish-voting")
    void updateLoggedInUser() {
        User user = getLoggedInUser();
        user.setFinishVoting(true);
        userRepository.save(user);
    }
}
