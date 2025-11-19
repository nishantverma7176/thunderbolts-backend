package com.verma.loginapi.controller;

import com.verma.loginapi.dto.ApiResponse;
import com.verma.loginapi.dto.GeminiMessageRequest;
import com.verma.loginapi.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
@CrossOrigin(origins = "*")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    // SINGLE CHAT ENDPOINT
    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<String>> chat(@RequestBody GeminiMessageRequest request) {
        try {
            String response = geminiService.healthChat(request.getMessage());
            return ResponseEntity.ok(ApiResponse.success(response, "Health response"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Error: " + e.getMessage()));
        }
    }

    // SINGLE TEST ENDPOINT
    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> test() {
        try {
            String response = geminiService.healthChat("Briefly introduce yourself as a health advisor in 1 sentences.");
            return ResponseEntity.ok(ApiResponse.success(response, "Health advisor test"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Test failed: " + e.getMessage()));
        }
    }
}