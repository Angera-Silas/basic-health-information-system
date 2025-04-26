package com.angerasilas.health_system_backend.controller;

import com.angerasilas.health_system_backend.dto.DashboardSummaryDto;
import com.angerasilas.health_system_backend.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDto> getDashboardSummary() {
        DashboardSummaryDto summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/summary/{doctorId}")
    public ResponseEntity<DashboardSummaryDto> getDashboardSummaryByDoctorId(@PathVariable Long doctorId) {
        DashboardSummaryDto summary = dashboardService.getDashboardSummaryByDoctorId(doctorId);
        return ResponseEntity.ok(summary);
    }
}