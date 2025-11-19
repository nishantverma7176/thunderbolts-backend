package com.verma.loginapi.dto;

public class HealthMetricRequest {
    private Integer systolic;
    private Integer diastolic;
    private Integer sugarLevel;
    private Integer heartRate;

    public Integer getSystolic() { return systolic; }
    public Integer getDiastolic() { return diastolic; }
    public Integer getSugarLevel() { return sugarLevel; }
    public Integer getHeartRate() { return heartRate; }

    public void setSystolic(Integer systolic) { this.systolic = systolic; }
    public void setDiastolic(Integer diastolic) { this.diastolic = diastolic; }
    public void setSugarLevel(Integer sugarLevel) { this.sugarLevel = sugarLevel; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }
}
