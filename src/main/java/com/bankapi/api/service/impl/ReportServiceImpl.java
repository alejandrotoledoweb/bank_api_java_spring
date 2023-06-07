package com.bankapi.api.service.impl;

import com.bankapi.api.dto.ReportDto;
import com.bankapi.api.models.Movement;
import com.bankapi.api.repository.MovementRepository;
import com.bankapi.api.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    private MovementRepository movementRepository;

    public ReportServiceImpl(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Override
    public List<ReportDto> getReportByDate(String startDate, String endDate, long accountId) {
        System.out.println("StarDate :"+ startDate);
        System.out.println("EndDate :"+ endDate);
        System.out.println("AccountId :"+ accountId);

        ZoneId zoneId = ZoneId.systemDefault();
        Instant startDateInstance = Instant.parse(startDate);
        Instant endDateInstance = Instant.parse(endDate);
        LocalDate startDateLDT = LocalDate.ofInstant(startDateInstance, zoneId);
        LocalDate endDateLDT = LocalDate.ofInstant(endDateInstance, zoneId);
        LocalDateTime startOfDay = startDateLDT.atStartOfDay();
        LocalDateTime endOfDay = endDateLDT.plusDays(1).atStartOfDay().minusNanos(1);

        System.out.println("STARTDATE: " + startOfDay);
        System.out.println("ENDDATE" + endOfDay);

        List<Movement> movementsList = movementRepository.findByAccountIdAndDateBetween(accountId, startOfDay, endOfDay);
        System.out.println(movementsList.size());
        return movementsList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ReportDto mapToDto(Movement movement) {
        ReportDto reportDto = new ReportDto();

        LocalDateTime singleDate = movement.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = singleDate.format(formatter);

        reportDto.setDate(formattedDate);
        reportDto.setClient(movement.getAccount().getClient().getName());
        reportDto.setAccountNumber(movement.getAccount().getAccountNumber());
        reportDto.setAccountType(movement.getAccount().getAccountType());
        reportDto.setInitialBalance(movement.getInitialBalance());
        reportDto.setState(movement.getAccount().getState());
        reportDto.setValue(movement.getValue());
        reportDto.setBalance(movement.getBalance());

        return reportDto;

    }
}
