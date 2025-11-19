package com.verma.loginapi.controller;

import com.verma.loginapi.dto.CallRequest;
import com.verma.loginapi.dto.CallResponse;
import com.verma.loginapi.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CallController {

    @Autowired
    private CallService callService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/call")
    public void handleCall(CallRequest request) {
        CallResponse response = callService.processCall(request);

        // Send event to receiver
        messagingTemplate.convertAndSend(
                "/topic/call/" + request.getReceiverPhone(),
                response
        );

        // Optionally notify caller as well
        messagingTemplate.convertAndSend(
                "/topic/call/" + request.getCallerPhone(),
                response
        );
    }
}
