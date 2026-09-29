package com.sumanth.analytics.controller;

import com.sumanth.analytics.dto.ReportRequest;
import com.sumanth.analytics.dto.ReportResponse;
import com.sumanth.analytics.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/metadata")
    public Map<String, Object> metadata() {
        return reportService.metadata();
    }

    @PostMapping("/dynamic-view")
    public ReportResponse dynamicView(@Valid @RequestBody ReportRequest request) {
        return reportService.generate(request);
    }
}
