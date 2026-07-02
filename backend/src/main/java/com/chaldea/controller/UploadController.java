package com.chaldea.controller;

import com.chaldea.service.FileStorageService;
import com.chaldea.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private final FileStorageService fileStorageService;
    private final ProfileService profileService;

    public UploadController(FileStorageService fileStorageService, ProfileService profileService) {
        this.fileStorageService = fileStorageService;
        this.profileService = profileService;
    }

    @PostMapping("/avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(
            Authentication authentication,
            @RequestParam("file") MultipartFile file) {
        String avatarUrl = fileStorageService.storeFile(file);
        profileService.updateAvatar(authentication.getName(), avatarUrl);
        return ResponseEntity.ok(Map.of("avatarUrl", avatarUrl));
    }
}
