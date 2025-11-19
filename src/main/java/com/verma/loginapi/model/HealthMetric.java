package com.verma.loginapi.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "health_metrics")
public class HealthMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer systolic;
    private Integer diastolic;

    private Integer sugarLevel;

    private Integer heartRate;

    private LocalDate recordedDate;

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Integer getSystolic() { return systolic; }
    public Integer getDiastolic() { return diastolic; }
    public Integer getSugarLevel() { return sugarLevel; }
    public Integer getHeartRate() { return heartRate; }
    public LocalDate getRecordedDate() { return recordedDate; }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setSystolic(Integer systolic) { this.systolic = systolic; }
    public void setDiastolic(Integer diastolic) { this.diastolic = diastolic; }
    public void setSugarLevel(Integer sugarLevel) { this.sugarLevel = sugarLevel; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }
    public void setRecordedDate(LocalDate recordedDate) { this.recordedDate = recordedDate; }
}
