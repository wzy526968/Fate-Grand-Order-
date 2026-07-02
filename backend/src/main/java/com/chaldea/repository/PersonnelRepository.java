package com.chaldea.repository;

import com.chaldea.model.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    List<Personnel> findByDepartmentOrderBySortOrder(String department);
    List<Personnel> findByStatusOrderBySortOrder(String status);
    List<Personnel> findAllByOrderBySortOrder();
}
