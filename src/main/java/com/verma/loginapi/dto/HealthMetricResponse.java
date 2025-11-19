package com.verma.loginapi.dto;

import java.time.LocalDate;

public class HealthMetricResponse {
    private LocalDate date;
    private Integer systolic;
    private Integer diastolic;
    private Integer sugarLevel;
    private Integer heartRate;

    public HealthMetricResponse(LocalDate date, Integer systolic, Integer diastolic, Integer sugarLevel, Integer heartRate) {
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.sugarLevel = sugarLevel;
        this.heartRate = heartRate;
    }

    public LocalDate getDate() { return date; }
    public Integer getSystolic() { return systolic; }
    public Integer getDiastolic() { return diastolic; }
    public Integer getSugarLevel() { return sugarLevel; }
    public Integer getHeartRate() { return heartRate; }
}
