package com.chaldea.dto;

import java.time.LocalDate;

public class CoffinDTO {
    private Long id;
    private String designation;
    private String status;
    private String occupantName;
    private String occupantClass;
    private Integer damageLevel;
    private Boolean isProtagonist;

    // Enriched fields
    private Double temperature;
    private Double humidity;
    private Integer cryoStability;
    private Double coolantPressure;
    private LocalDate lastMaintenance;
    private LocalDate maintenanceDue;
    private String maintenanceNotes;
    private String loreText;

    public CoffinDTO() {}

    public CoffinDTO(Long id, String designation, String status, String occupantName,
                     String occupantClass, Integer damageLevel, Boolean isProtagonist,
                     Double temperature, Double humidity, Integer cryoStability,
                     Double coolantPressure, LocalDate lastMaintenance,
                     LocalDate maintenanceDue, String maintenanceNotes, String loreText) {
        this.id = id;
        this.designation = designation;
        this.status = status;
        this.occupantName = occupantName;
        this.occupantClass = occupantClass;
        this.damageLevel = damageLevel;
        this.isProtagonist = isProtagonist;
        this.temperature = temperature;
        this.humidity = humidity;
        this.cryoStability = cryoStability;
        this.coolantPressure = coolantPressure;
        this.lastMaintenance = lastMaintenance;
        this.maintenanceDue = maintenanceDue;
        this.maintenanceNotes = maintenanceNotes;
        this.loreText = loreText;
    }

    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getOccupantName() { return occupantName; }
    public void setOccupantName(String occupantName) { this.occupantName = occupantName; }

    public String getOccupantClass() { return occupantClass; }
    public void setOccupantClass(String occupantClass) { this.occupantClass = occupantClass; }

    public Integer getDamageLevel() { return damageLevel; }
    public void setDamageLevel(Integer damageLevel) { this.damageLevel = damageLevel; }

    public Boolean getIsProtagonist() { return isProtagonist; }
    public void setIsProtagonist(Boolean isProtagonist) { this.isProtagonist = isProtagonist; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }

    public Integer getCryoStability() { return cryoStability; }
    public void setCryoStability(Integer cryoStability) { this.cryoStability = cryoStability; }

    public Double getCoolantPressure() { return coolantPressure; }
    public void setCoolantPressure(Double coolantPressure) { this.coolantPressure = coolantPressure; }

    public LocalDate getLastMaintenance() { return lastMaintenance; }
    public void setLastMaintenance(LocalDate lastMaintenance) { this.lastMaintenance = lastMaintenance; }

    public LocalDate getMaintenanceDue() { return maintenanceDue; }
    public void setMaintenanceDue(LocalDate maintenanceDue) { this.maintenanceDue = maintenanceDue; }

    public String getMaintenanceNotes() { return maintenanceNotes; }
    public void setMaintenanceNotes(String maintenanceNotes) { this.maintenanceNotes = maintenanceNotes; }

    public String getLoreText() { return loreText; }
    public void setLoreText(String loreText) { this.loreText = loreText; }
}
