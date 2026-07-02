package com.chaldea.controller;

import com.chaldea.dto.LoginRequest;
import com.chaldea.dto.LoginResponse;
import com.chaldea.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(Authentication authentication) {
        String username = authentication.getName();
        LoginResponse userInfo = authService.getUserInfo(username);
        return ResponseEntity.ok(Map.of(
                "username", userInfo.getUsername(),
                "displayName", userInfo.getDisplayName(),
                "role", userInfo.getRole()
        ));
    }
}
