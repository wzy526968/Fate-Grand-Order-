package com.chaldea.dto;

public class UpdateProfileRequest {
    private String bio;
    private Long favoriteServantId;
    private String magicCircuitQuality;

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public Long getFavoriteServantId() { return favoriteServantId; }
    public void setFavoriteServantId(Long favoriteServantId) { this.favoriteServantId = favoriteServantId; }
    public String getMagicCircuitQuality() { return magicCircuitQuality; }
    public void setMagicCircuitQuality(String magicCircuitQuality) { this.magicCircuitQuality = magicCircuitQuality; }
}
