package com.verma.loginapi.service;

import com.verma.loginapi.dto.MessageRequest;
import com.verma.loginapi.dto.MessageResponse;
import com.verma.loginapi.model.Chat;
import com.verma.loginapi.model.Message;
import com.verma.loginapi.model.User;
import com.verma.loginapi.repository.ChatRepository;
import com.verma.loginapi.repository.MessageRepository;
import com.verma.loginapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    // ✅ For WebSocket messages
    public MessageResponse saveAndReturnMessage(MessageRequest messageRequest) {
        User sender = userRepository.findByPhoneNumber(messageRequest.getSenderPhone()).orElseThrow();
        User receiver = userRepository.findByPhoneNumber(messageRequest.getReceiverPhone()).orElseThrow();
        Chat chat = chatService.getOrCreateChat(sender, receiver);

        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(messageRequest.getContent());
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);

        return new MessageResponse(
                sender.getPhoneNumber(),
                receiver.getPhoneNumber(),
                message.getContent(),
                message.getTimestamp()
        );
    }

    // ✅ For REST endpoint (/api/chat/send)
    public Message saveMessage(Chat chat, User sender, String content) {
        Message message = new Message();
        message.setChat(chat);
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getChatMessages(Chat chat) {
        return messageRepository.findByChatOrderByTimestampAsc(chat);
    }
}
