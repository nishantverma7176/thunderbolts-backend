package com.verma.loginapi.dto;

public class MedicineResponse {
    private boolean success;
    private String message;
    private Object data;

    // Constructors
    public MedicineResponse() {}

    public MedicineResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Static factory methods
    public static MedicineResponse success(Object data, String message) {
        return new MedicineResponse(true, message, data);
    }

    public static MedicineResponse error(String message) {
        return new MedicineResponse(false, message, null);
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}