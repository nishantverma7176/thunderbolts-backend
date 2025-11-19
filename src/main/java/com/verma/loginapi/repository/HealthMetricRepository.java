package com.verma.loginapi.repository;

import com.verma.loginapi.model.HealthMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HealthMetricRepository extends JpaRepository<HealthMetric, Long> {

    List<HealthMetric> findByUserIdAndRecordedDateBetween(
            Long userId,
            LocalDate start,
            LocalDate end
    );

    boolean existsByUserIdAndRecordedDate(Long userId, LocalDate date);
}
