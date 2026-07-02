package com.chaldea.dto;

import java.time.LocalDateTime;

public class SystemMetricsDTO {
    private Long id;
    private String metricKey;
    private Double metricValue;
    private LocalDateTime recordedAt;

    public SystemMetricsDTO() {}

    public SystemMetricsDTO(Long id, String metricKey, Double metricValue, LocalDateTime recordedAt) {
        this.id = id;
        this.metricKey = metricKey;
        this.metricValue = metricValue;
        this.recordedAt = recordedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMetricKey() { return metricKey; }
    public void setMetricKey(String metricKey) { this.metricKey = metricKey; }

    public Double getMetricValue() { return metricValue; }
    public void setMetricValue(Double metricValue) { this.metricValue = metricValue; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}
