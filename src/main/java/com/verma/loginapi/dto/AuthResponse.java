package com.verma.loginapi.dto;

public class AuthResponse {
    private boolean success;
    private String message;
    private UserData user;
    private String token;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AuthResponse(boolean success, String message, UserData user, String token) {
        this.success = success;
        this.message = message;
        this.user = user;
        this.token = token;
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public UserData getUser() { return user; }
    public void setUser(UserData user) { this.user = user; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    // Inner class for user data
    public static class UserData {
        private Long id;
        private String name;
        private String email;
        private String phoneNumber;

        public UserData(Long id, String name, String email, String phoneNumber) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        // Getters
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhoneNumber() { return phoneNumber; }
    }
}