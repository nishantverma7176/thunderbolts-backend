package com.verma.loginapi.service;

import com.verma.loginapi.dto.HealthMetricRequest;
import com.verma.loginapi.model.HealthMetric;
import com.verma.loginapi.model.User;
import com.verma.loginapi.repository.HealthMetricRepository;
import com.verma.loginapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class HealthMetricService {

    @Autowired
    private HealthMetricRepository repo;

    @Autowired
    private UserRepository userRepo;

    // Existing method (for today’s date)
    public void saveDailyMetrics(Long userId, HealthMetricRequest request) {
        saveDailyMetricsWithDate(userId, request, LocalDate.now());
    }

    // New method that allows custom date
    public void saveDailyMetricsWithDate(Long userId, HealthMetricRequest request, LocalDate date) {

        if (repo.existsByUserIdAndRecordedDate(userId, date)) {
            throw new RuntimeException("Health data already saved for " + date);
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HealthMetric metric = new HealthMetric();
        metric.setUser(user);
        metric.setSystolic(request.getSystolic());
        metric.setDiastolic(request.getDiastolic());
        metric.setSugarLevel(request.getSugarLevel());
        metric.setHeartRate(request.getHeartRate());
        metric.setRecordedDate(date);

        repo.save(metric);
    }

    public List<HealthMetric> getLast7(Long userId) {
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(6);
        return repo.findByUserIdAndRecordedDateBetween(userId, weekAgo, today);
    }
}
