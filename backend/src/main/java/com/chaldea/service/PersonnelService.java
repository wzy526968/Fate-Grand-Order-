package com.chaldea.service;

import com.chaldea.dto.PersonnelDTO;
import com.chaldea.model.Personnel;
import com.chaldea.repository.PersonnelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonnelService {

    private final PersonnelRepository personnelRepository;

    public PersonnelService(PersonnelRepository personnelRepository) {
        this.personnelRepository = personnelRepository;
    }

    public List<PersonnelDTO> getAllPersonnel() {
        return personnelRepository.findAllByOrderBySortOrder().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PersonnelDTO getPersonnelById(Long id) {
        Personnel p = personnelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personnel not found: " + id));
        return toDTO(p);
    }

    public List<PersonnelDTO> getPersonnelByDepartment(String department) {
        return personnelRepository.findByDepartmentOrderBySortOrder(department).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PersonnelDTO createPersonnel(PersonnelDTO dto) {
        Personnel p = toEntity(dto);
        p = personnelRepository.save(p);
        return toDTO(p);
    }

    public PersonnelDTO updatePersonnel(Long id, PersonnelDTO dto) {
        Personnel p = personnelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personnel not found: " + id));
        p.setName(dto.getName());
        p.setTitle(dto.getTitle());
        p.setDepartment(dto.getDepartment());
        p.setRole(dto.getRole());
        p.setStatus(dto.getStatus());
        p.setAvatarUrl(dto.getAvatarUrl());
        p.setBio(dto.getBio());
        p.setLoreText(dto.getLoreText());
        p.setSortOrder(dto.getSortOrder());
        p = personnelRepository.save(p);
        return toDTO(p);
    }

    public void deletePersonnel(Long id) {
        personnelRepository.deleteById(id);
    }

    private PersonnelDTO toDTO(Personnel p) {
        PersonnelDTO dto = new PersonnelDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setTitle(p.getTitle());
        dto.setDepartment(p.getDepartment());
        dto.setRole(p.getRole());
        dto.setStatus(p.getStatus());
        dto.setAvatarUrl(p.getAvatarUrl());
        dto.setBio(p.getBio());
        dto.setLoreText(p.getLoreText());
        dto.setSortOrder(p.getSortOrder());
        return dto;
    }

    private Personnel toEntity(PersonnelDTO dto) {
        Personnel p = new Personnel();
        p.setName(dto.getName());
        p.setTitle(dto.getTitle());
        p.setDepartment(dto.getDepartment());
        p.setRole(dto.getRole());
        p.setStatus(dto.getStatus());
        p.setAvatarUrl(dto.getAvatarUrl());
        p.setBio(dto.getBio());
        p.setLoreText(dto.getLoreText());
        p.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);
        return p;
    }
}
