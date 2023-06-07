package com.bankapi.api.repository;

import com.bankapi.api.models.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    Movement findTopByAccountIdAndMovementTypeOrderByDateDesc(long accountId, String moveType);

    List<Movement> findByAccountIdOrderByDateDesc(long accountId);

    List<Movement> findByDateBetween(LocalDateTime start, LocalDateTime end);
    List<Movement> findByAccountIdAndDateBetween(long accountId,LocalDateTime start, LocalDateTime end);


}
