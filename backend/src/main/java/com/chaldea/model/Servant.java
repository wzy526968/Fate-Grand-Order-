package com.chaldea.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "servants")
@JsonIgnoreProperties({"servantClass"})
public class Servant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ServantClass servantClass;

    @Column(length = 200)
    private String title;

    private Integer rarity = 5;

    private Integer strength;
    private Integer endurance;  
    private Integer agility;     
    private Integer magic;   
    private Integer luck;
    private Integer np;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String imageUrl;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ServantClass getServantClass() { return servantClass; }
    public void setServantClass(ServantClass servantClass) { this.servantClass = servantClass; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getRarity() { return rarity; }
    public void setRarity(Integer rarity) { this.rarity = rarity; }
    public Integer getStrength() { return strength; }
    public void setStrength(Integer strength) { this.strength = strength; }
    public Integer getEndurance() { return endurance; }
    public void setEndurance(Integer endurance) { this.endurance = endurance; }
    public Integer getAgility() { return agility; }
    public void setAgility(Integer agility) { this.agility = agility; }
    public Integer getMagic() { return magic; }
    public void setMagic(Integer magic) { this.magic = magic; }
    public Integer getLuck() { return luck; }
    public void setLuck(Integer luck) { this.luck = luck; }
    public Integer getNp() { return np; }
    public void setNp(Integer np) { this.np = np; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
