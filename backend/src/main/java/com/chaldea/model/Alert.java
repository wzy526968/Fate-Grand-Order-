package com.chaldea.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String severity; // CRITICAL, WARNING, INFO

    @Column(nullable = false)
    private String source;   // SHEBA, TRISMEGISTUS, COOLING, COFFIN

    @Column(nullable = false, length = 500)
    private String message;

    @Column(name = "is_acknowledged")
    private Boolean acknowledged = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

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
