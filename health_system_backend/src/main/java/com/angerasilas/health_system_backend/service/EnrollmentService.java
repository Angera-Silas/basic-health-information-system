package com.angerasilas.health_system_backend.service;

import com.angerasilas.health_system_backend.dto.EnrollmentDto;
import com.angerasilas.health_system_backend.dto.EnrollmentInformationDto;

import java.util.List;

public interface EnrollmentService {
    List<EnrollmentDto> getAllEnrollments();
    EnrollmentDto getEnrollmentById(Long id);
    EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto);
    EnrollmentDto updateEnrollment(Long id, EnrollmentDto enrollmentDto);
    void deleteEnrollment(Long id);

    EnrollmentInformationDto getEnrollmentInformationById(Long enrollmentId);
    List<EnrollmentInformationDto> getAllEnrollmentInformation();
    List<EnrollmentInformationDto> getEnrollmentInformationByClientId(Long clientId);
    List<EnrollmentInformationDto> getEnrollmentInformationByProgramId(Long programId);
    List<EnrollmentInformationDto> getEnrollmentInformationByDoctorId(Long doctorId);
}