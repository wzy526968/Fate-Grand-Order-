package com.chaldea.service;

import com.chaldea.dto.ServantDTO;
import com.chaldea.exception.ResourceNotFoundException;
import com.chaldea.model.Servant;
import com.chaldea.model.ServantClass;
import com.chaldea.repository.ServantClassRepository;
import com.chaldea.repository.ServantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServantService {

    private final ServantRepository servantRepository;
    private final ServantClassRepository classRepository;

    public ServantService(ServantRepository servantRepository,
                          ServantClassRepository classRepository) {
        this.servantRepository = servantRepository;
        this.classRepository = classRepository;
    }

    public List<ServantDTO> getAllServants() {
        return servantRepository.findAllWithClass().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ServantDTO getServantById(Long id) {
        Servant servant = servantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servant", id));
        return toDTO(servant);
    }

    public List<ServantDTO> getServantsByClass(Long classId) {
        return servantRepository.findByServantClassId(classId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ServantDTO createServant(ServantDTO dto) {
        Servant servant = new Servant();
        applyDtoToEntity(dto, servant);
        Servant saved = servantRepository.save(servant);
        return toDTO(saved);
    }

    @Transactional
    public ServantDTO updateServant(Long id, ServantDTO dto) {
        Servant servant = servantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servant", id));
        applyDtoToEntity(dto, servant);
        Servant saved = servantRepository.save(servant);
        return toDTO(saved);
    }

    @Transactional
    public void deleteServant(Long id) {
        Servant servant = servantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servant", id));
        servantRepository.delete(servant);
    }

    private void applyDtoToEntity(ServantDTO dto, Servant servant) {
        servant.setName(dto.getName());
        servant.setTitle(dto.getTitle());
        servant.setRarity(dto.getRarity());
        servant.setStrength(dto.getStrength());
        servant.setEndurance(dto.getEndurance());
        servant.setAgility(dto.getAgility());
        servant.setMagic(dto.getMagic());
        servant.setLuck(dto.getLuck());
        servant.setNp(dto.getNp());
        servant.setDescription(dto.getDescription());

        // Resolve class by classId or className
        if (dto.getClassId() != null) {
            ServantClass sc = classRepository.findById(dto.getClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("ServantClass", dto.getClassId()));
            servant.setServantClass(sc);
        } else if (dto.getClassName() != null) {
            ServantClass sc = classRepository.findByClassName(dto.getClassName())
                    .orElseThrow(() -> new ResourceNotFoundException("ServantClass: " + dto.getClassName()));
            servant.setServantClass(sc);
        }
    }

    private ServantDTO toDTO(Servant s) {
        ServantDTO dto = new ServantDTO();
        dto.setId(s.getId());
        dto.setName(s.getName());
        if (s.getServantClass() != null) {
            dto.setClassId(s.getServantClass().getId());
            dto.setClassName(s.getServantClass().getClassName());
            dto.setClassIcon(s.getServantClass().getIcon());
        }
        dto.setTitle(s.getTitle());
        dto.setRarity(s.getRarity());
        dto.setStrength(s.getStrength());
        dto.setEndurance(s.getEndurance());
        dto.setAgility(s.getAgility());
        dto.setMagic(s.getMagic());
        dto.setLuck(s.getLuck());
        dto.setNp(s.getNp());
        dto.setDescription(s.getDescription());
        return dto;
    }
}
