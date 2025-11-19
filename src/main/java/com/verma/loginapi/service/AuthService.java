package com.verma.loginapi.service;

import com.verma.loginapi.dto.*;
import com.verma.loginapi.model.PasswordResetToken;
import com.verma.loginapi.model.User;
import com.verma.loginapi.repository.PasswordResetRepository;
import com.verma.loginapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private UserRepository userRepository;

    private static final int OTP_EXPIRY_MINUTES = 10;

    // ✅ Register
    public AuthResponse register(RegisterRequest registerRequest) {
        try {
            User newUser = new User(
                    registerRequest.getName(),
                    registerRequest.getEmail(),
                    registerRequest.getPhoneNumber(),
                    registerRequest.getPassword() // no encoding here
            );

            User saved = userService.registerUser(newUser);

            AuthResponse.UserData userData = new AuthResponse.UserData(
                    saved.getId(),
                    saved.getName(),
                    saved.getEmail(),
                    saved.getPhoneNumber()
            );

            return new AuthResponse(true, "User registered successfully", userData, "dummy-token");
        } catch (Exception e) {
            return new AuthResponse(false, e.getMessage());
        }
    }

    // ✅ Login
    public AuthResponse login(LoginRequest request) {
        Optional<User> userOpt = userService.findByEmailOrPhoneNumber(request.getUsername());

        if (userOpt.isEmpty()) return new AuthResponse(false, "Invalid credentials");

        User user = userOpt.get();

        // Debug if needed
        // System.out.println("Entered: " + request.getPassword());
        // System.out.println("Stored: " + user.getPassword());
        // System.out.println("Match: " + passwordEncoder.matches(request.getPassword(), user.getPassword()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse(false, "Invalid credentials");
        }

        AuthResponse.UserData userData = new AuthResponse.UserData(
                user.getId(), user.getName(), user.getEmail(), user.getPhoneNumber()
        );

        return new AuthResponse(true, "Login successful", userData, generateToken(user));
    }

    private String generateToken(User user) {
        return "jwt-token-" + user.getId() + "-" + System.currentTimeMillis();
    }

    // ✅ Forgot password
    public ApiResponse forgotPassword(ForgotPasswordRequest request) {
        Optional<User> userOpt = userService.findByEmailOrPhoneNumber(request.getEmail());
        if (userOpt.isEmpty()) return new ApiResponse(false, "No user found with that email");

        String otp = generateOtp();
        PasswordResetToken token = passwordResetRepository.findByEmail(request.getEmail())
                .orElse(new PasswordResetToken());

        token.setEmail(request.getEmail());
        token.setOtp(otp);
        token.setExpiryAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        passwordResetRepository.save(token);

        emailService.sendSimpleMessage(
                request.getEmail(),
                "Your OTP Code",
                "Your OTP is " + otp + ". It will expire in 10 minutes."
        );

        return new ApiResponse(true, "OTP sent successfully");
    }

    // ✅ Verify OTP
    public ApiResponse verifyOtp(VerifyOtpRequest request) {
        Optional<PasswordResetToken> token = passwordResetRepository.findByEmailAndOtp(request.getEmail(), request.getOtp());
        if (token.isEmpty() || token.get().getExpiryAt().isBefore(LocalDateTime.now())) {
            return new ApiResponse(false, "Invalid or expired OTP");
        }
        return new ApiResponse(true, "OTP verified");
    }

    // ✅ Reset password
    @Transactional
    public ApiResponse resetPassword(ResetPasswordRequest request) {
        Optional<PasswordResetToken> tokenOpt = passwordResetRepository.findByEmailAndOtp(request.getEmail(), request.getOtp());
        if (tokenOpt.isEmpty() || tokenOpt.get().getExpiryAt().isBefore(LocalDateTime.now())) {
            return new ApiResponse(false, "Invalid or expired OTP");
        }

        User user = userService.findByEmailOrPhoneNumber(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        passwordResetRepository.deleteByEmail(request.getEmail());

        return new ApiResponse(true, "Password reset successful");
    }

    // ✅ Change password
    public ApiResponse changePassword(ChangePasswordRequest request) {
        User user = userService.findByEmailOrPhoneNumber(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return new ApiResponse(false, "Old password incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return new ApiResponse(true, "Password changed successfully");
    }

    // Utility
    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}
