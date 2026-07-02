package com.chaldea.dto;

import java.time.LocalDateTime;

public class AlertDTO {
    private Long id;
    private String severity;
    private String source;
    private String message;
    private Boolean acknowledged;
    private LocalDateTime createdAt;

    public AlertDTO() {}

    public AlertDTO(Long id, String severity, String source, String message,
                    Boolean acknowledged, LocalDateTime createdAt) {
        this.id = id;
        this.severity = severity;
        this.source = source;
        this.message = message;
        this.acknowledged = acknowledged;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Boolean getAcknowledged() { return acknowledged; }
    public void setAcknowledged(Boolean acknowledged) { this.acknowledged = acknowledged; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
