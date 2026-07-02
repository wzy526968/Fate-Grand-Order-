package com.chaldea.service;

import com.chaldea.dto.*;
import com.chaldea.model.*;
import com.chaldea.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TechnicsService {

    private static final Map<String, Long> CONVERSION_RATES = Map.of(
            "MANA_PRISM:QP", 10_000L
    );

    private final CoffinRepository coffinRepository;
    private final ResourceRepository resourceRepository;
    private final SystemMetricsRepository systemMetricsRepository;
    private final AlertRepository alertRepository;
    private final EventLogRepository eventLogRepository;

    public TechnicsService(CoffinRepository coffinRepository,
                           ResourceRepository resourceRepository,
                           SystemMetricsRepository systemMetricsRepository,
                           AlertRepository alertRepository,
                           EventLogRepository eventLogRepository) {
        this.coffinRepository = coffinRepository;
        this.resourceRepository = resourceRepository;
        this.systemMetricsRepository = systemMetricsRepository;
        this.alertRepository = alertRepository;
        this.eventLogRepository = eventLogRepository;
    }

    // --- Coffins ---

    @Transactional(readOnly = true)
    public List<CoffinDTO> getAllCoffins() {
        return coffinRepository.findAllByOrderByDesignationAsc()
                .stream()
                .map(this::toCoffinDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CoffinDTO getCoffinById(Long id) {
        return coffinRepository.findById(id)
                .map(this::toCoffinDTO)
                .orElse(null);
    }

    // --- Resources ---

    @Transactional(readOnly = true)
    public List<ResourceDTO> getAllResources() {
        return resourceRepository.findAll()
                .stream()
                .map(this::toResourceDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResourceDTO convertResource(ConvertRequest request) {
        String key = request.getFromType() + ":" + request.getToType();
        Long rate = CONVERSION_RATES.get(key);
        if (rate == null) {
            throw new IllegalArgumentException("Unsupported conversion: " + key);
        }

        Resource from = resourceRepository.findByType(request.getFromType())
                .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + request.getFromType()));
        Resource to = resourceRepository.findByType(request.getToType())
                .orElseThrow(() -> new IllegalArgumentException("Resource not found: " + request.getToType()));

        long cost = request.getFromQty();
        if (from.getQuantity() < cost) {
            throw new IllegalStateException("Insufficient " + request.getFromType()
                    + ": have " + from.getQuantity() + ", need " + cost);
        }

        from.setQuantity(from.getQuantity() - cost);
        to.setQuantity(to.getQuantity() + cost * rate);

        resourceRepository.save(from);
        resourceRepository.save(to);

        return toResourceDTO(to);
    }

    // --- System Metrics ---

    @Transactional(readOnly = true)
    public List<SystemMetricsDTO> getMetricsHistory(String metricKey, int limit) {
        return systemMetricsRepository.findTop20ByMetricKeyOrderByRecordedAtDesc(metricKey)
                .stream()
                .limit(limit)
                .map(this::toSystemMetricsDTO)
                .collect(Collectors.toList());
    }

    // --- Alerts ---

    @Transactional(readOnly = true)
    public List<AlertDTO> getActiveAlerts() {
        return alertRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(this::toAlertDTO)
                .collect(Collectors.toList());
    }

    // --- Event Logs ---

    @Transactional(readOnly = true)
    public List<EventLogDTO> getRecentEvents(int limit) {
        return eventLogRepository.findTop50ByOrderByCreatedAtDesc()
                .stream()
                .limit(limit)
                .map(this::toEventLogDTO)
                .collect(Collectors.toList());
    }

    // --- DTO mappers ---

    private CoffinDTO toCoffinDTO(Coffin c) {
        return new CoffinDTO(
                c.getId(), c.getDesignation(), c.getStatus(),
                c.getOccupantName(), c.getOccupantClass(),
                c.getDamageLevel(), c.getIsProtagonist(),
                c.getTemperature(), c.getHumidity(), c.getCryoStability(),
                c.getCoolantPressure(), c.getLastMaintenance(),
                c.getMaintenanceDue(), c.getMaintenanceNotes(), c.getLoreText()
        );
    }

    private ResourceDTO toResourceDTO(Resource r) {
        return new ResourceDTO(r.getId(), r.getName(), r.getType(), r.getQuantity(), r.getIcon());
    }

    private SystemMetricsDTO toSystemMetricsDTO(SystemMetrics m) {
        return new SystemMetricsDTO(m.getId(), m.getMetricKey(), m.getMetricValue(), m.getRecordedAt());
    }

    private AlertDTO toAlertDTO(Alert a) {
        return new AlertDTO(a.getId(), a.getSeverity(), a.getSource(),
                a.getMessage(), a.getAcknowledged(), a.getCreatedAt());
    }

    private EventLogDTO toEventLogDTO(EventLog e) {
        return new EventLogDTO(e.getId(), e.getEventType(), e.getMessage(), e.getCreatedAt());
    }
}
