package com.verma.loginapi.dto;

public class MedicineRequest {
    private String query;

    // Constructors
    public MedicineRequest() {}

    public MedicineRequest(String query) {
        this.query = query;
    }

    // Getters and Setters
    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }
}