package com.chaldea.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 255)
    private String avatarUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_servant_id")
    private Servant favoriteServant;

    @Column(length = 50)
    private String magicCircuitQuality;

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
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public Servant getFavoriteServant() { return favoriteServant; }
    public void setFavoriteServant(Servant favoriteServant) { this.favoriteServant = favoriteServant; }
    public String getMagicCircuitQuality() { return magicCircuitQuality; }
    public void setMagicCircuitQuality(String magicCircuitQuality) { this.magicCircuitQuality = magicCircuitQuality; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
