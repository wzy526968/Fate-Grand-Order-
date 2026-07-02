package com.chaldea.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_metrics")
public class SystemMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metric_key", nullable = false)
    private String metricKey; // cpu_load, temperature, memory_usage, disk_usage

    @Column(name = "metric_value", nullable = false)
    private Double metricValue;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMetricKey() { return metricKey; }
    public void setMetricKey(String metricKey) { this.metricKey = metricKey; }

    public Double getMetricValue() { return metricValue; }
    public void setMetricValue(Double metricValue) { this.metricValue = metricValue; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
