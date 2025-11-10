package com.verma.loginapi.service;

import com.verma.loginapi.model.User;
import com.verma.loginapi.dto.AuthResponse;
import com.verma.loginapi.dto.LoginRequest;
import com.verma.loginapi.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest registerRequest) {
        try {
            User user = new User(
                    registerRequest.getName(),
                    registerRequest.getEmail(),
                    registerRequest.getPhoneNumber(),
                    registerRequest.getPassword()
            );

            User savedUser = userService.registerUser(user);

            AuthResponse.UserData userData = new AuthResponse.UserData(
                    savedUser.getId(),
                    savedUser.getName(),
                    savedUser.getEmail(),
                    savedUser.getPhoneNumber()
            );

            return new AuthResponse(true, "User registered successfully", userData, "dummy-token");

        } catch (RuntimeException e) {
            return new AuthResponse(false, e.getMessage());
        }
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userService.findByEmailOrPhoneNumber(loginRequest.getUsername());

        if (userOptional.isEmpty()) {
            return new AuthResponse(false, "Invalid credentials");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new AuthResponse(false, "Invalid credentials");
        }

        AuthResponse.UserData userData = new AuthResponse.UserData(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber()
        );

        // In a real application, generate JWT token here
        String token = generateToken(user);

        return new AuthResponse(true, "Login successful", userData, token);
    }

    private String generateToken(User user) {
        // For now, return a dummy token
        // In real implementation, use JWT library to generate token
        return "jwt-token-" + user.getId() + "-" + System.currentTimeMillis();
    }
}