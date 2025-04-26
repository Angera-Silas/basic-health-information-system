package com.angerasilas.health_system_backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDto {
    private long totalClients;
    private long activePrograms;
    private long totalEnrollments;
    private long totalDoctors;
    private List<RecentProgram> recentPrograms;
    private List<RecentClients> recentClients;
}