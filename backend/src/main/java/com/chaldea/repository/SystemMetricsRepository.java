package com.chaldea.repository;

import com.chaldea.model.SystemMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SystemMetricsRepository extends JpaRepository<SystemMetrics, Long> {
    List<SystemMetrics> findTop20ByMetricKeyOrderByRecordedAtDesc(String metricKey);
    List<SystemMetrics> findByMetricKeyAndRecordedAtAfterOrderByRecordedAtAsc(String metricKey, LocalDateTime after);
}
