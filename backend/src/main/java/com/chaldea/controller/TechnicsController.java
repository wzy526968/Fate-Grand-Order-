package com.chaldea.controller;

import com.chaldea.dto.*;
import com.chaldea.service.TechnicsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technics")
public class TechnicsController {

    private final TechnicsService technicsService;

    public TechnicsController(TechnicsService technicsService) {
        this.technicsService = technicsService;
    }

    @GetMapping("/coffins")
    public ResponseEntity<List<CoffinDTO>> getAllCoffins() {
        return ResponseEntity.ok(technicsService.getAllCoffins());
    }

    @GetMapping("/coffins/{id}")
    public ResponseEntity<CoffinDTO> getCoffinById(@PathVariable Long id) {
        CoffinDTO coffin = technicsService.getCoffinById(id);
        if (coffin == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coffin);
    }

    @GetMapping("/resources")
    public ResponseEntity<List<ResourceDTO>> getAllResources() {
        return ResponseEntity.ok(technicsService.getAllResources());
    }

    @PutMapping("/resources/convert")
    public ResponseEntity<?> convertResource(@RequestBody ConvertRequest request) {
        try {
            ResourceDTO result = technicsService.convertResource(request);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(java.util.Map.of("error", e.getMessage()));
        }
    }

    // --- System Metrics ---

    @GetMapping("/metrics/{metricKey}/history")
    public ResponseEntity<List<SystemMetricsDTO>> getMetricsHistory(
            @PathVariable String metricKey,
            @RequestParam(defaultValue = "20") int limit) {
        return ResponseEntity.ok(technicsService.getMetricsHistory(metricKey, limit));
    }

    // --- Alerts ---

    @GetMapping("/alerts")
    public ResponseEntity<List<AlertDTO>> getActiveAlerts() {
        return ResponseEntity.ok(technicsService.getActiveAlerts());
    }

    // --- Event Logs ---

    @GetMapping("/events")
    public ResponseEntity<List<EventLogDTO>> getRecentEvents(
            @RequestParam(defaultValue = "50") int limit) {
        return ResponseEntity.ok(technicsService.getRecentEvents(limit));
    }
}
