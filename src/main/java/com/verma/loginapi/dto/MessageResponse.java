package com.verma.loginapi.dto;

import java.time.LocalDateTime;

public class MessageResponse {
    private String senderPhone;
    private String receiverPhone;
    private String content;
    private LocalDateTime timestamp;

    public MessageResponse() {}
    public MessageResponse(String senderPhone, String receiverPhone, String content, LocalDateTime timestamp) {
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSenderPhone() { return senderPhone; }
    public void setSenderPhone(String senderPhone) { this.senderPhone = senderPhone; }

    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
