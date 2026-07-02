package com.chaldea.repository;

import com.chaldea.model.Coffin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffinRepository extends JpaRepository<Coffin, Long> {
    List<Coffin> findAllByOrderByDesignationAsc();
}
