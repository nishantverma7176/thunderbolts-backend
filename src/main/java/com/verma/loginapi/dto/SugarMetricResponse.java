package com.verma.loginapi.dto;

import java.time.LocalDate;

public class SugarMetricResponse {
    private LocalDate date;
    private Integer sugarLevel;

    public SugarMetricResponse(LocalDate date, Integer sugarLevel) {
        this.date = date;
        this.sugarLevel = sugarLevel;
    }

    public LocalDate getDate() { return date; }
    public Integer getSugarLevel() { return sugarLevel; }
}
