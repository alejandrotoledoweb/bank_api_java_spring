package com.bankapi.api.controllers;

import com.bankapi.api.dto.ReportDto;
import com.bankapi.api.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReportController {
    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/account/{accountId}/movements/report")
    public ResponseEntity<List<ReportDto>> getReport(@RequestParam("startDate")String startDate, @RequestParam("endDate") String endDate, @PathVariable(value="accountId") long accountId) {
        return new ResponseEntity<>(reportService.getReportByDate(startDate, endDate, accountId), HttpStatus.OK);

    }
}
