package com.example.ToDoApp.controller;


import com.example.ToDoApp.model.AppUser;
import com.example.ToDoApp.repository.UserRepository;
import com.example.ToDoApp.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void registerUser_Success() throws Exception {
        Map<String, String> user = new HashMap<>();
        user.put("email", "test@example.com");
        user.put("password", "password");

        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        String response = authController.register(user);
        assertEquals("User registered successfully!", response);
        verify(userRepository).save(any(AppUser.class));
    }

    @Test
    void registerUser_EmailTaken() throws Exception {
        Map<String, String> user = new HashMap<>();
        user.put("email", "test@example.com");
        user.put("password", "password");

        when(userRepository.existsByEmail(any(String.class))).thenReturn(true);

        String response = authController.register(user);
        assertEquals("Error: Email is already taken!", response);
        verify(userRepository, never()).save(any(AppUser.class));
    }

    @Test
    void loginUser_Success() throws Exception {
        Map<String, String> user = new HashMap<>();
        user.put("email", "test@example.com");
        user.put("password", "password");

        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtUtils.generateJwtToken(any(String.class))).thenReturn("dummyToken");

        Map<String, String> response = authController.login(user);
        assertEquals("dummyToken", response.get("token"));
    }
}
