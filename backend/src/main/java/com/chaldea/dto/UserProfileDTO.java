package com.chaldea.dto;

public class UserProfileDTO {
    private Long userId;
    private String username;
    private String displayName;
    private String avatarUrl;
    private String bio;
    private Long favoriteServantId;
    private String favoriteServantName;
    private String magicCircuitQuality;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public Long getFavoriteServantId() { return favoriteServantId; }
    public void setFavoriteServantId(Long favoriteServantId) { this.favoriteServantId = favoriteServantId; }
    public String getFavoriteServantName() { return favoriteServantName; }
    public void setFavoriteServantName(String favoriteServantName) { this.favoriteServantName = favoriteServantName; }
    public String getMagicCircuitQuality() { return magicCircuitQuality; }
    public void setMagicCircuitQuality(String magicCircuitQuality) { this.magicCircuitQuality = magicCircuitQuality; }
}
