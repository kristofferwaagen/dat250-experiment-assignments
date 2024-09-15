package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.domain.PollManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final PollManager pollManager;

    public UserController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    // 1. Create a new user
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Check if the user already exists
        if (pollManager.getUser(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
        
        pollManager.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created");
    }

    // 2. List all users
    @GetMapping
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = pollManager.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users); // No users found, return 204 No Content
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);  // Return users with 200 OK
    }

    // 3. Clear all users
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearAllUsers() {
        pollManager.clearUsers();
        return ResponseEntity.ok("All users cleared");
    }

}
