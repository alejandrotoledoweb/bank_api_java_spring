package com.bankapi.api.service.impl;

import com.bankapi.api.dto.MovementDto;
import com.bankapi.api.exceptions.AccountNotFoundException;
import com.bankapi.api.exceptions.ClientNotFoundException;
import com.bankapi.api.exceptions.MovementBadRequestException;
import com.bankapi.api.exceptions.MovementNotFoundException;
import com.bankapi.api.models.Account;
import com.bankapi.api.models.Client;
import com.bankapi.api.models.Movement;
import com.bankapi.api.repository.AccountRepository;
import com.bankapi.api.repository.MovementRepository;
import com.bankapi.api.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovementServiceImpl implements MovementService {


    private MovementRepository movementRepository;
    private AccountRepository accountRepository;

    private final int maxWithdraw = 1000;

    @Autowired
    public MovementServiceImpl(MovementRepository movementRepository, AccountRepository accountRepository) {
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public MovementDto createFirstMovement(long accountId, MovementDto movementDto) {
        Movement movement = mapToEntity(movementDto);
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new MovementNotFoundException("Account with this id could not be found"));

        movement.setBalance(movement.getValue());
        LocalDateTime now = LocalDateTime.now();
        movement.setDate(now);
        movement.setAccount(account);
        movement.setInitialBalance(0L);
        Movement newMovement = movementRepository.save(movement);

        return mapToDto(newMovement);

    }


    @Override
    public MovementDto createMovement(long accountId, MovementDto movementDto) {
        Movement movement = mapToEntity(movementDto);

        Account account = accountRepository.findById(accountId).orElseThrow(()-> new MovementNotFoundException("Account with this id could not be found"));
        movement.setAccount(account);
        LocalDateTime now = LocalDateTime.now();
        movement.setDate(now);

        boolean isCorrectType = Objects.equals(account.getAccountType(), movement.getMovementType());
        if (!isCorrectType) {
            throw new MovementBadRequestException("Movement type could not be processed");
        }

        Movement lastMovement = movementRepository.findTopByAccountIdAndMovementTypeOrderByDateDesc(accountId, movement.getMovementType());
        long lastMovementBalance = 0L;
        if (lastMovement != null) {
            lastMovementBalance = lastMovement.getBalance();
        }
        long movementValue = movement.getValue();

        movement.setInitialBalance(lastMovementBalance);
        long newBalance = lastMovementBalance + movementValue;
        movement.setBalance(newBalance);


        LocalDate localDate = LocalDate.now();
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.plusDays(1).atStartOfDay().minusNanos(1);

        System.out.println("move ser: " + startOfDay);
        List<Movement> lastTodayMovements = movementRepository.findByDateBetween(startOfDay,endOfDay);
        long totalAmountForToday = calculateTotal(lastTodayMovements);

        if( movement.getValue() < 0) {
            totalAmountForToday += movement.getValue();
        }

        long absTotalValue = Math.abs(totalAmountForToday);
        if (absTotalValue > maxWithdraw && movement.getValue() < 0) {
            throw new MovementBadRequestException("Cupo diario excedido");
        }

        if (newBalance <= 0) {
            throw new MovementBadRequestException("Saldo no disponible");
        }

        Movement newMovement = movementRepository.save(movement);

        return mapToDto(newMovement);
    }

    @Override
    public List<MovementDto> getAllMovements(long accountId) {
        List<Movement> movements = movementRepository.findByAccountIdOrderByDateDesc(accountId);

        if(movements == null) {
            throw new MovementNotFoundException("Movements not found");
        }
        return movements.stream().map(review -> mapToDto(review)).collect(Collectors.toList());

    }

    @Override
    public MovementDto getMovementById(long accountId, long id) {
        Movement movement = movementRepository.findById(id).orElseThrow(()-> new MovementNotFoundException("Movement could not be found"));
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new AccountNotFoundException("Account associated could not be found"));

        if(movement.getAccount().getId() != account.getId()) {
            throw new MovementNotFoundException("This movement does not belong to the account");
        }
        return mapToDto(movement);
    }

    @Override
    public MovementDto updateMovement(long accountId,long id, MovementDto movementDto) {
        Movement newMovement = mapToEntity(movementDto);
        Account account = accountRepository.findById(accountId).orElseThrow(()->
                new AccountNotFoundException("Account associated with the movement not found"));
        Movement movement = movementRepository.findById(id).orElseThrow((()->
                new MovementNotFoundException("Account with associated client not found")));

        if(movement.getAccount().getId() != account.getId()) {
            throw new AccountNotFoundException("This account does not belong to the client");
        }

        movement.setDate(newMovement.getDate());
        movement.setMovementType(newMovement.getMovementType());
        movement.setValue(newMovement.getValue());
        movement.setBalance(newMovement.getBalance());
        movement.setInitialBalance(newMovement.getInitialBalance());

        Movement updatedMovement = movementRepository.save(movement);
        return mapToDto(updatedMovement);
    }

    @Override
    public MovementDto updateMovementPatch(long accountId, long id, MovementDto movementDto) {

        return null;
    }

    @Override
    public void deleteMovement(long accountId, long id) {
        Account account = accountRepository.findById(accountId).orElseThrow((()-> new AccountNotFoundException("Account id does not exist")));
        Movement movement = movementRepository.findById(id).orElseThrow(()-> new MovementNotFoundException("Movement associated with the account not found"));

        if(movement.getAccount().getId() != account.getId()) {
            throw new MovementNotFoundException("This movement does not belong to the account");
        }

        movementRepository.delete(movement);

    }

    private Movement mapToEntity(MovementDto movementDto) {
        Movement movement = new Movement();

        movement.setId(movementDto.getId());
        movement.setDate(movementDto.getDate());
        movement.setMovementType(movementDto.getMovementType());
        movement.setValue(movementDto.getValue());
        movement.setBalance(movementDto.getBalance());
        movement.setInitialBalance(movementDto.getInitialBalance());

        return movement;
    }

    private MovementDto mapToDto(Movement movement) {
        MovementDto movementDto = new MovementDto();

        movementDto.setId(movement.getId());
        movementDto.setDate(movement.getDate());
        movementDto.setMovementType(movement.getMovementType());
        movementDto.setValue(movement.getValue());
        movementDto.setBalance(movement.getBalance());
        movementDto.setInitialBalance(movement.getInitialBalance());
        movementDto.setAccountNumber(movement.getAccount().getAccountNumber());
        movementDto.setAccountType(movement.getAccount().getAccountType());
        movementDto.setStatus(movement.getAccount().getState());

        return movementDto;
    }

    private long calculateTotal(List<Movement> movementsList) {
        long totalAmount= 0L;
        for (Movement movement : movementsList) {
            long temp = movement.getValue();
            if (temp < 0L) {
                totalAmount += temp;
            }
        }
        return totalAmount;
    }
}
