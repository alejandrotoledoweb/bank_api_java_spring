package com.bankapi.api.service;

import com.bankapi.api.dto.ReportDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReportService {

    List<ReportDto> getReportByDate(String startDate, String endDate, long accountId);

}
