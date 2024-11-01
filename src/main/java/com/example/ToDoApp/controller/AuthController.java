package com.example.ToDoApp.controller;



import com.example.ToDoApp.model.AppUser; // Import your AppUser model
import com.example.ToDoApp.repository.UserRepository;
import com.example.ToDoApp.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> user) {
        if (userRepository.existsByEmail(user.get("email"))) {
            return "Error: Email is already taken!";
        }

        // Create a new AppUser instead of User
        AppUser newUser = new AppUser(); // Change User to AppUser
        newUser.setEmail(user.get("email"));
        newUser.setPassword(passwordEncoder.encode(user.get("password")));
        userRepository.save(newUser);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.get("email"), user.get("password"))
        );
        String token = jwtUtils.generateJwtToken(user.get("email"));
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
