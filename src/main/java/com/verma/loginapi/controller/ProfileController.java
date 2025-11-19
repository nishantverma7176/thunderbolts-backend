package com.verma.loginapi.controller;

import com.verma.loginapi.dto.ApiResponse;
import com.verma.loginapi.dto.ProfileResponse;
import com.verma.loginapi.dto.ProfileUpdateRequest;
import com.verma.loginapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@RequestParam String email) {
        try {
            ProfileResponse profile = userService.getUserProfile(email);
            return ResponseEntity.ok(ApiResponse.success(profile, "Profile retrieved successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(
            @RequestParam String email,
            @Valid @RequestBody ProfileUpdateRequest request) {
        try {
            ProfileResponse updatedProfile = userService.updateUserProfile(email, request);
            return ResponseEntity.ok(ApiResponse.success(updatedProfile, "Profile updated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfileById(@PathVariable Long userId) {
        try {
            ProfileResponse profile = userService.getUserProfileById(userId);
            return ResponseEntity.ok(ApiResponse.success(profile, "Profile retrieved successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}