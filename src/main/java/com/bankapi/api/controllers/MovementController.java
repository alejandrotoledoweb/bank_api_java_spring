package com.bankapi.api.controllers;

import com.bankapi.api.dto.ClientDto;
import com.bankapi.api.dto.MovementDto;
import com.bankapi.api.service.MovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovementController {

    private MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping("/account/{accountId}/movements")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MovementDto> createMovement(@PathVariable(value="accountId") long accountId,@Valid @RequestBody MovementDto movementDto) {

        return new ResponseEntity<>(movementService.createMovement(accountId, movementDto), HttpStatus.CREATED);
    }

    @GetMapping("/account/{accountId}/movements")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MovementDto>> getMovementByAccountId(@PathVariable(value = "accountId") long accountId) {
        return new ResponseEntity<>(movementService.getAllMovements(accountId), HttpStatus.OK);

    }

    @GetMapping("/account/{accountId}/movements/{id}")
    public ResponseEntity<MovementDto> getMovementById(@PathVariable(value = "accountId") long accountId,
                                                       @PathVariable(value="id") long id) {
        return new ResponseEntity<>(movementService.getMovementById(accountId, id), HttpStatus.OK);
    }

    @PutMapping("/account/{accountId}/movements/{id}")
    public ResponseEntity<MovementDto> updateMovement(@PathVariable(value="accountId") long accountId,
                                                      @PathVariable(value="id") long id,
                                                      @Valid @RequestBody MovementDto movementDto) {
        System.out.println("inside controller");
        return new ResponseEntity<>(movementService.updateMovement(accountId, id, movementDto), HttpStatus.ACCEPTED);
    }




}
