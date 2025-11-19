package com.verma.loginapi.service;

import com.verma.loginapi.dto.ProfileResponse;
import com.verma.loginapi.dto.ProfileUpdateRequest;
import com.verma.loginapi.model.User;
import com.verma.loginapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ User registration — single point of password encoding
    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException("Phone number is already registered");
        }

        // Encode password once here
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // ✅ Corrected query call
    public Optional<User> findByEmailOrPhoneNumber(String identifier) {
        return userRepository.findByEmailOrPhoneNumber(identifier, identifier);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public ProfileResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // Add this method to your existing UserService class
    public ProfileResponse getUserProfileById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        return new ProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
    @Transactional
    public ProfileResponse updateUserProfile(String email, ProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getEmail().equals(request.getEmail())) {
            userRepository.findByEmail(request.getEmail())
                    .ifPresent(existingUser -> {
                        if (!existingUser.getId().equals(user.getId())) {
                            throw new RuntimeException("Email already taken");
                        }
                    });
        }

        if (!user.getPhoneNumber().equals(request.getPhoneNumber())) {
            userRepository.findByPhoneNumber(request.getPhoneNumber())
                    .ifPresent(existingUser -> {
                        if (!existingUser.getId().equals(user.getId())) {
                            throw new RuntimeException("Phone number already taken");
                        }
                    });
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        User updated = userRepository.save(user);

        return new ProfileResponse(
                updated.getId(),
                updated.getName(),
                updated.getEmail(),
                updated.getPhoneNumber(),
                updated.getCreatedAt(),
                updated.getUpdatedAt()
        );
    }
}
