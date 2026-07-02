package com.chaldea.repository;

import com.chaldea.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);

    @Query("SELECT p FROM UserProfile p JOIN FETCH p.user u WHERE u.id = :userId")
    Optional<UserProfile> findByUserIdWithUser(Long userId);

    @Query("SELECT p FROM UserProfile p JOIN FETCH p.user u LEFT JOIN FETCH p.favoriteServant WHERE u.id = :userId")
    Optional<UserProfile> findByUserIdWithAll(Long userId);
}
