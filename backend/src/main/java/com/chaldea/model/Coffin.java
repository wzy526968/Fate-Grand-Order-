package com.chaldea.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "coffins")
public class Coffin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private String status; // ACTIVE, DAMAGED, FROZEN, DESTROYED, MAINTENANCE

    private String occupantName;

    private String occupantClass;

    private Integer damageLevel; // 0–100

    @Column(name = "is_protagonist")
    private Boolean isProtagonist = false;

    // --- New enrichment fields ---

    private Double temperature;       // cryo temperature in Celsius

    private Double humidity;          // internal humidity %

    @Column(name = "cryo_stability")
    private Integer cryoStability;    // 0–100 stability rating

    @Column(name = "coolant_pressure")
    private Double coolantPressure;   // coolant system PSI

    @Column(name = "last_maintenance")
    private LocalDate lastMaintenance;

    @Column(name = "maintenance_due")
    private LocalDate maintenanceDue;

    @Column(name = "maintenance_notes", length = 500)
    private String maintenanceNotes;

    @Column(name = "lore_text", length = 2000)
    private String loreText;

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
