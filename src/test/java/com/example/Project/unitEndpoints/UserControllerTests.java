package com.example.Project.unitEndpoints;

import com.example.Project.controllers.AuthController;
import com.example.Project.model.entities.User;
import com.example.Project.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    private final UserService userService = mock(UserService.class);
    private final AuthController authController = new AuthController(userService);

    @Test
    @DisplayName("Login with valid credentials")
    void testLogin_ValidCredentials() {
        String username = "User";
        String password = "rawPassword";
        String token = UUID.randomUUID().toString();

        User user = new User();
        user.setUsername(username);
        user.setAuthToken(token);

        when(userService.validateCredentials(username, password)).thenReturn(true);
        when(userService.findByUsername(username)).thenReturn(Optional.of(user));

        ResponseEntity<String> response = authController.login(username, password);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Token:"));
        verify(userService, times(1)).validateCredentials(username, password);
        verify(userService, times(1)).findByUsername(username);
        verify(userService, times(1)).updateUser(user);
    }

    @Test
    @DisplayName("Login with invalid credentials")
    void testLogin_InvalidCredentials() {
        String username = "User";
        String password = "wrongPassword";

        when(userService.validateCredentials(username, password)).thenReturn(false);

        ResponseEntity<String> response = authController.login(username, password);

        assertNotNull(response);
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
        verify(userService, times(1)).validateCredentials(username, password);
        verify(userService, never()).findByUsername(anyString());
        verify(userService, never()).updateUser(any(User.class));
    }

    @Test
    @DisplayName("Register user successfully")
    void testRegisterUser_Success() {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("rawPassword");

        when(userService.registerUser(user)).thenReturn(user);

        ResponseEntity<String> response = authController.register(user);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully", response.getBody());
        verify(userService, times(1)).registerUser(user);
    }


    @Test
    @DisplayName("Register user with existing username")
    void testRegisterUser_UsernameExists() {
        User user = new User();
        user.setUsername("existingUser");

        doThrow(new IllegalArgumentException("Username already exists"))
                .when(userService)
                .registerUser(user);

        ResponseEntity<String> response = authController.register(user);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Username already exists", response.getBody());
        verify(userService, times(1)).registerUser(user);
    }
}
