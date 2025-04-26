package com.angerasilas.health_system_backend.service.impl;

import com.angerasilas.health_system_backend.dto.DashboardSummaryDto;
import com.angerasilas.health_system_backend.dto.RecentClients;
import com.angerasilas.health_system_backend.dto.RecentProgram;
import com.angerasilas.health_system_backend.repository.ClientDetailsRepository;
import com.angerasilas.health_system_backend.repository.DoctorDetailsRepository;
import com.angerasilas.health_system_backend.repository.EnrollmentRepository;
import com.angerasilas.health_system_backend.repository.ProgramRepository;
import com.angerasilas.health_system_backend.service.DashboardService;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ClientDetailsRepository clientDetailsRepository;
    private final ProgramRepository programRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final DoctorDetailsRepository doctorDetailsRepository;

    public DashboardServiceImpl(ClientDetailsRepository clientDetailsRepository,
            ProgramRepository programRepository,
            EnrollmentRepository enrollmentRepository,
            DoctorDetailsRepository doctorDetailsRepository) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.programRepository = programRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.doctorDetailsRepository = doctorDetailsRepository;
    }

    @Override
    public DashboardSummaryDto getDashboardSummary() {
        long totalClients = clientDetailsRepository.count();
        long activePrograms = programRepository.count();
        long totalEnrollments = enrollmentRepository.count();
        long totalDoctors = doctorDetailsRepository.count();
        List<RecentProgram> recentProgram = programRepository.findAllRecentPrograms();
        List<RecentClients> recentClients = clientDetailsRepository.findRecentClients();

        return new DashboardSummaryDto(totalClients, activePrograms, totalEnrollments, totalDoctors, recentProgram,
                recentClients);
    }

    @Override
    public DashboardSummaryDto getDashboardSummaryByDoctorId(Long doctorId) {
        long totalClients = clientDetailsRepository.countClientsByDoctorId(doctorId);
        long activePrograms = programRepository.countProgramsByDoctorId(doctorId);
        long totalEnrollments = enrollmentRepository.countEnrollmentsByDoctorId(doctorId);
        long totalDoctors = 1;
        List<RecentProgram> recentProgram = programRepository.findRecentProgramsByDoctorId(doctorId);
        List<RecentClients> recentClients = clientDetailsRepository.findRecentClientsByDoctor(doctorId);
        return new DashboardSummaryDto(totalClients, activePrograms, totalEnrollments, totalDoctors, recentProgram,
                recentClients);
    }
}