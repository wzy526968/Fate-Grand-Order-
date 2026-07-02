package com.chaldea.repository;

import com.chaldea.model.Servant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServantRepository extends JpaRepository<Servant, Long> {

    Optional<Servant> findByName(String name);

    List<Servant> findByServantClassId(Long classId);

    @Query("SELECT s FROM Servant s JOIN FETCH s.servantClass sc ORDER BY sc.id, s.id")
    List<Servant> findAllWithClass();
}
