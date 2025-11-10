package com.verma.loginapi.service;

import com.verma.loginapi.model.Chat;
import com.verma.loginapi.model.User;
import com.verma.loginapi.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Chat getOrCreateChat(User user1, User user2) {
        Optional<Chat> chat = chatRepository.findByUser1AndUser2(user1, user2);
        if (chat.isEmpty()) {
            chat = chatRepository.findByUser2AndUser1(user1, user2);
        }
        if (chat.isPresent()) {
            return chat.get();
        } else {
            Chat newChat = new Chat(user1, user2);
            return chatRepository.save(newChat);
        }
    }
}
