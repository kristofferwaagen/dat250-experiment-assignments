package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.domain.PollManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PollManager pollManager;

    public UserController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    // 1. Create a new user
    @PostMapping
    public String createUser(@RequestBody User user) {
        pollManager.addUser(user);
        return "User created";
    }

    // 2. List all users
    @GetMapping
    public List<User> listAllUsers() {
        return pollManager.getAllUsers();
    }
}
