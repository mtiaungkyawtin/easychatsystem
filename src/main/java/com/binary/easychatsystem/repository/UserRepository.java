package com.binary.easychatsystem.repository;

import com.binary.easychatsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findByLastSeenAfter(LocalDateTime dateTime);
    List<User> findByFullNameContainingIgnoreCase(String fullName);

    @Query("SELECT u FROM User u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<User> findUsersCreatedBetween(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    @Query("SELECT u FROM User u ORDER BY u.lastSeen DESC NULLS LAST")
    List<User> findAllOrderByLastSeenDesc();
}
