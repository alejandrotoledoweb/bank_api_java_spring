package com.bankapi.api.service;

import com.bankapi.api.dto.MovementDto;
import com.bankapi.api.dto.MovementResponse;

import java.util.List;

public interface MovementService {

    MovementDto createFirstMovement(long accountId, MovementDto movementDto);
    MovementDto createMovement(long accountId, MovementDto movementDto);
    List<MovementDto> getAllMovements(long accountId);

    MovementDto getMovementById(long id, long accountId);

    MovementDto updateMovement(long accountId,long id, MovementDto movementDto);

    MovementDto updateMovementPatch(long accountId, long id, MovementDto movementDto);

    void deleteMovement(long id);
}
