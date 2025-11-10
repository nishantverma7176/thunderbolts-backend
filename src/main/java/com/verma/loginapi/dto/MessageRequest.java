package com.verma.loginapi.dto;

public class MessageRequest {
    private String senderPhone;
    private String receiverPhone;
    private String content;

    public String getSenderPhone() { return senderPhone; }
    public void setSenderPhone(String senderPhone) { this.senderPhone = senderPhone; }

    public String getReceiverPhone() { return receiverPhone; }
    public void setReceiverPhone(String receiverPhone) { this.receiverPhone = receiverPhone; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
