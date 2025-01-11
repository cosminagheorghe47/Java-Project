package com.example.Project.controllers;

import com.example.Project.model.entities.User;
import com.example.Project.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Operations for user login and registration")
public class AuthController {
    private final UserService userService;

    @Operation(summary = "User login",
            description = "Authenticates a user based on username and password, and returns a JWT token if valid.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        boolean isValid = userService.validateCredentials(username, password);
        if (isValid) {
            String token = UUID.randomUUID().toString();
            User user = userService.findByUsername(username).get();
            user.setAuthToken(token);
            userService.updateUser(user);

            return ResponseEntity.ok("Token: " + token);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @Operation(summary = "User registration",
            description = "Registers a new user in the system.")
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
