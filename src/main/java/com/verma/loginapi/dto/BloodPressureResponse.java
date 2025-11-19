package com.verma.loginapi.dto;

import java.time.LocalDate;

public class BloodPressureResponse {
    private LocalDate date;
    private Integer systolic;
    private Integer diastolic;

    public BloodPressureResponse(LocalDate date, Integer systolic, Integer diastolic) {
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    public LocalDate getDate() { return date; }
    public Integer getSystolic() { return systolic; }
    public Integer getDiastolic() { return diastolic; }
}
