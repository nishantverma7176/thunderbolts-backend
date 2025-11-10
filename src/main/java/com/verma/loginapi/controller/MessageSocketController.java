package com.verma.loginapi.controller;

import com.verma.loginapi.dto.MessageRequest;
import com.verma.loginapi.dto.MessageResponse;
import com.verma.loginapi.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageSocketController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageResponse sendMessage(MessageRequest messageRequest) {
        return messageService.saveAndReturnMessage(messageRequest);
    }
}
