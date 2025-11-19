package com.verma.loginapi.controller;

import com.verma.loginapi.dto.*;
import com.verma.loginapi.service.HealthMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/health")
public class HealthMetricController {

    @Autowired
    private HealthMetricService service;

    /**
     * Add daily data for a user.
     * If ?date=YYYY-MM-DD is passed, that date will be used.
     * Otherwise, today's date will be used automatically.
     */
    @PostMapping("/add/{userId}")
    public ResponseEntity<?> addMetrics(
            @PathVariable Long userId,
            @RequestBody HealthMetricRequest request,
            @RequestParam(required = false) String date) {

        LocalDate recordedDate;
        if (date != null && !date.isEmpty()) {
            recordedDate = LocalDate.parse(date);
        } else {
            recordedDate = LocalDate.now();
        }

        service.saveDailyMetricsWithDate(userId, request, recordedDate);
        return ResponseEntity.ok("Health metrics saved for date: " + recordedDate);
    }

    // Sugar level 7-day data
    @GetMapping("/sugar/last7days/{userId}")
    public ResponseEntity<List<SugarMetricResponse>> getSugar(@PathVariable Long userId) {
        List<SugarMetricResponse> response = service.getLast7(userId)
                .stream()
                .map(m -> new SugarMetricResponse(m.getRecordedDate(), m.getSugarLevel()))
                .toList();
        return ResponseEntity.ok(response);
    }

    // Blood pressure 7-day data
    @GetMapping("/bp/last7days/{userId}")
    public ResponseEntity<List<BloodPressureResponse>> getBP(@PathVariable Long userId) {
        List<BloodPressureResponse> response = service.getLast7(userId)
                .stream()
                .map(m -> new BloodPressureResponse(m.getRecordedDate(), m.getSystolic(), m.getDiastolic()))
                .toList();
        return ResponseEntity.ok(response);
    }

    // Heart rate 7-day data
    @GetMapping("/hr/last7days/{userId}")
    public ResponseEntity<List<HeartRateResponse>> getHeartRate(@PathVariable Long userId) {
        List<HeartRateResponse> response = service.getLast7(userId)
                .stream()
                .map(m -> new HeartRateResponse(m.getRecordedDate(), m.getHeartRate()))
                .toList();
        return ResponseEntity.ok(response);
    }
}
