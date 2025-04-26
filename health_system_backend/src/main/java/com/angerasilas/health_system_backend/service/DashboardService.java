package com.angerasilas.health_system_backend.service;

import com.angerasilas.health_system_backend.dto.DashboardSummaryDto;

public interface DashboardService {
    DashboardSummaryDto getDashboardSummary();
    DashboardSummaryDto getDashboardSummaryByDoctorId(Long doctorId);
}