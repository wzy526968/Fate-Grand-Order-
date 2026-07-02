package com.chaldea.controller;

import com.chaldea.dto.UpdateProfileRequest;
import com.chaldea.dto.UserProfileDTO;
import com.chaldea.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(profileService.getProfile(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileDTO> updateMyProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(profileService.updateProfile(authentication.getName(), request));
    }
}
