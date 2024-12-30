package com.example.Project.unitService;

import com.example.Project.model.entities.User;
import com.example.Project.repositories.UserRepository;
import com.example.Project.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setId(1);
        user.setUsername("User");

        when(userRepository.findByUsername("User")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("User");

        assertTrue(result.isPresent());
        assertEquals("User", result.get().getUsername());
        verify(userRepository, times(1)).findByUsername("User");
    }

    @Test
    void testFindByUsername_NotFound() {
        when(userRepository.findByUsername("User")).thenReturn(Optional.empty());

        Optional<User> result = userService.findByUsername("User");

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByUsername("User");
    }

    @Test
    void testValidateCredentials_Valid() {
        User user = new User();
        user.setUsername("User");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("User")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);

        boolean result = userService.validateCredentials("User", "rawPassword");

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("User");
        verify(passwordEncoder, times(1)).matches("rawPassword", "encodedPassword");
    }

    @Test
    void testValidateCredentials_Invalid() {
        when(userRepository.findByUsername("User")).thenReturn(Optional.empty());

        boolean result = userService.validateCredentials("User", "wrongPassword");

        assertFalse(result);
        verify(userRepository, times(1)).findByUsername("User");
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void testRegisterUser_Success() {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("rawPassword");

        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository, times(1)).findByUsername("newUser");
        verify(passwordEncoder, times(1)).encode("rawPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testRegisterUser_UsernameExists() {
        User existingUser = new User();
        existingUser.setUsername("existingUser");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));

        User newUser = new User();
        newUser.setUsername("existingUser");

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(newUser));
        verify(userRepository, times(1)).findByUsername("existingUser");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
