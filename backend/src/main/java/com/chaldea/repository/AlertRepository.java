package com.chaldea.repository;

import com.chaldea.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findTop10ByOrderByCreatedAtDesc();
    List<Alert> findBySeverityOrderByCreatedAtDesc(String severity);
}
