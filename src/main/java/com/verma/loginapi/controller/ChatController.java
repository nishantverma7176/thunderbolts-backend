package com.verma.loginapi.controller;

import com.verma.loginapi.model.Chat;
import com.verma.loginapi.model.Message;
import com.verma.loginapi.model.User;
import com.verma.loginapi.repository.UserRepository;
import com.verma.loginapi.service.ChatService;
import com.verma.loginapi.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageService messageService;

    // ✅ Enhanced Get chat history with detailed debugging
    @GetMapping("/history")
    public ResponseEntity<?> getChatHistory(@RequestParam String userPhone, @RequestParam String otherPhone) {
        try {
            System.out.println("🔍 History API Called - userPhone: '" + userPhone + "', otherPhone: '" + otherPhone + "'");

            // Debug: List all users in database
            List<User> allUsers = userRepository.findAll();
            System.out.println("📋 All users in DB: " +
                    allUsers.stream()
                            .map(u -> u.getPhoneNumber() + "(" + u.getName() + ")")
                            .collect(Collectors.joining(", ")));

            // Find users with detailed logging
            Optional<User> u1Opt = userRepository.findByPhoneNumber(userPhone);
            Optional<User> u2Opt = userRepository.findByPhoneNumber(otherPhone);

            System.out.println("🔎 User1 found: " + u1Opt.isPresent());
            System.out.println("🔎 User2 found: " + u2Opt.isPresent());

            if (u1Opt.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "User not found: " + userPhone,
                        "availableUsers", allUsers.stream().map(User::getPhoneNumber).collect(Collectors.toList())
                ));
            }

            if (u2Opt.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "User not found: " + otherPhone,
                        "availableUsers", allUsers.stream().map(User::getPhoneNumber).collect(Collectors.toList())
                ));
            }

            User u1 = u1Opt.get();
            User u2 = u2Opt.get();

            System.out.println("✅ Users found - User1: " + u1.getName() + " (" + u1.getPhoneNumber() +
                    "), User2: " + u2.getName() + " (" + u2.getPhoneNumber() + ")");

            Chat chat = chatService.getOrCreateChat(u1, u2);
            System.out.println("💬 Chat ID: " + chat.getId());

            List<Message> messages = messageService.getChatMessages(chat);
            System.out.println("📨 Messages found: " + messages.size());

            return ResponseEntity.ok(messages);

        } catch (Exception e) {
            System.err.println("❌ Error in getChatHistory: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", e.getMessage(),
                    "debug", "Check server logs for detailed error information"
            ));
        }
    }

    // ✅ Send message safely with validation (unchanged)
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> payload) {
        try {
            String senderPhone = payload.get("senderPhone");
            String receiverPhone = payload.get("receiverPhone");
            String content = payload.get("content");

            if (senderPhone == null || receiverPhone == null || content == null || content.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "senderPhone, receiverPhone, and content are required"));
            }

            User sender = userRepository.findByPhoneNumber(senderPhone)
                    .orElseThrow(() -> new RuntimeException("Sender not found: " + senderPhone));
            User receiver = userRepository.findByPhoneNumber(receiverPhone)
                    .orElseThrow(() -> new RuntimeException("Receiver not found: " + receiverPhone));

            Chat chat = chatService.getOrCreateChat(sender, receiver);
            Message savedMessage = messageService.saveMessage(chat, sender, content);

            return ResponseEntity.ok(savedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ New endpoint to debug users
    @GetMapping("/debug/users")
    public ResponseEntity<?> debugUsers() {
        List<User> users = userRepository.findAll();
        List<Map<String, String>> userList = users.stream()
                .map(user -> Map.of(
                        "id", user.getId().toString(),
                        "name", user.getName(),
                        "phoneNumber", user.getPhoneNumber(),
                        "email", user.getEmail()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "totalUsers", users.size(),
                "users", userList
        ));
    }
}