package com.chaldea.controller;

import com.chaldea.dto.ServantDTO;
import com.chaldea.service.ServantService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servants")
public class ServantController {

    private final ServantService servantService;

    public ServantController(ServantService servantService) {
        this.servantService = servantService;
    }

    @GetMapping
    public ResponseEntity<List<ServantDTO>> getAllServants() {
        return ResponseEntity.ok(servantService.getAllServants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServantDTO> getServantById(@PathVariable Long id) {
        return ResponseEntity.ok(servantService.getServantById(id));
    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<ServantDTO>> getServantsByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(servantService.getServantsByClass(classId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServantDTO> createServant(@RequestBody ServantDTO dto) {
        return ResponseEntity.ok(servantService.createServant(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServantDTO> updateServant(@PathVariable Long id, @RequestBody ServantDTO dto) {
        return ResponseEntity.ok(servantService.updateServant(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteServant(@PathVariable Long id) {
        servantService.deleteServant(id);
        return ResponseEntity.noContent().build();
    }
}
