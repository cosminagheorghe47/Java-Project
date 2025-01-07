package com.example.Project.controllers;

import com.example.Project.model.entities.User;
import com.example.Project.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        boolean isValid = userService.validateCredentials(username, password);
        System.out.println("aici1");
        if (isValid) {
            System.out.println("aici2");
            String token = UUID.randomUUID().toString();
            User user = userService.findByUsername(username).get();
            user.setAuthToken(token);
            userService.updateUser(user);

            return ResponseEntity.ok("Token: " + token);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
