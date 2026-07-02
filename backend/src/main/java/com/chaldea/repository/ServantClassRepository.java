package com.chaldea.repository;

import com.chaldea.model.ServantClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServantClassRepository extends JpaRepository<ServantClass, Long> {
    Optional<ServantClass> findByClassName(String className);
}
