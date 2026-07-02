package com.chaldea.controller;

import com.chaldea.dto.PersonnelDTO;
import com.chaldea.service.PersonnelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personnel")
public class PersonnelController {

    private final PersonnelService personnelService;

    public PersonnelController(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    @GetMapping
    public ResponseEntity<List<PersonnelDTO>> getAllPersonnel() {
        return ResponseEntity.ok(personnelService.getAllPersonnel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonnelDTO> getPersonnelById(@PathVariable Long id) {
        return ResponseEntity.ok(personnelService.getPersonnelById(id));
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<PersonnelDTO>> getPersonnelByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(personnelService.getPersonnelByDepartment(department));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PersonnelDTO> createPersonnel(@RequestBody PersonnelDTO dto) {
        return ResponseEntity.ok(personnelService.createPersonnel(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PersonnelDTO> updatePersonnel(@PathVariable Long id, @RequestBody PersonnelDTO dto) {
        return ResponseEntity.ok(personnelService.updatePersonnel(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePersonnel(@PathVariable Long id) {
        personnelService.deletePersonnel(id);
        return ResponseEntity.noContent().build();
    }
}
