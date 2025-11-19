package com.verma.loginapi.dto;

public class CallResponse {
    private String status; // "success" or "error"
    private String message;
    private String callerPhone;
    private String receiverPhone;
    private String action;

    public CallResponse() {}

    public CallResponse(String status, String message, String callerPhone, String receiverPhone, String action) {
        this.status = status;
        this.message = message;
        this.callerPhone = callerPhone;
        this.receiverPhone = receiverPhone;
        this.action = action;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCallerPhone() {
        return callerPhone;
    }

    public void setCallerPhone(String callerPhone) {
        this.callerPhone = callerPhone;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
