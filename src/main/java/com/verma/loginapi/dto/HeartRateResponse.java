package com.verma.loginapi.dto;

import java.time.LocalDate;

public class HeartRateResponse {
    private LocalDate date;
    private Integer heartRate;

    public HeartRateResponse(LocalDate date, Integer heartRate) {
        this.date = date;
        this.heartRate = heartRate;
    }

    public LocalDate getDate() { return date; }
    public Integer getHeartRate() { return heartRate; }
}
