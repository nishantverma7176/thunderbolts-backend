package com.verma.loginapi.dto;

public class GeminiMessageRequest {
    private String message;

    // Constructors
    public GeminiMessageRequest() {}

    public GeminiMessageRequest(String message) {
        this.message = message;
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}