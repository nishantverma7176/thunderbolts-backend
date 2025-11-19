package com.verma.loginapi.dto;

import lombok.Data;

@Data
public class LocationResponse {
    private String name;
    private double lat;
    private double lon;
    private String type; // hospital, clinic, pharmacy etc
}