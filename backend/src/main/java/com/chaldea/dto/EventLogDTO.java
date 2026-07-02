package com.chaldea.dto;

import java.time.LocalDateTime;

public class EventLogDTO {
    private Long id;
    private String eventType;
    private String message;
    private LocalDateTime createdAt;

    public EventLogDTO() {}

    public EventLogDTO(Long id, String eventType, String message, LocalDateTime createdAt) {
        this.id = id;
        this.eventType = eventType;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
