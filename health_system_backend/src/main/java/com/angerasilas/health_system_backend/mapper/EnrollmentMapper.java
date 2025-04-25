package com.angerasilas.health_system_backend.mapper;

import com.angerasilas.health_system_backend.dto.EnrollmentDto;
import com.angerasilas.health_system_backend.entity.Enrollment;

public class EnrollmentMapper {

    public static EnrollmentDto toDto(Enrollment enrollment) {
        return new EnrollmentDto(
                enrollment.getId(),
                enrollment.getClient().getId(),
                enrollment.getProgram().getId(),
                enrollment.getStartDate(),
                enrollment.getEndDate(),
                enrollment.getStatus(),
                enrollment.getProgress(),
                enrollment.getNotes(),
                enrollment.getEnrolledAt(),
                enrollment.getCreatedBy().getId()
        );
    }

    public static Enrollment toEntity(EnrollmentDto enrollmentDto) {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(enrollmentDto.getId());
        enrollment.setStartDate(enrollmentDto.getStartDate());
        enrollment.setEndDate(enrollmentDto.getEndDate());
        enrollment.setStatus(enrollmentDto.getStatus());
        enrollment.setProgress(enrollmentDto.getProgress());
        enrollment.setNotes(enrollmentDto.getNotes());
        enrollment.setEnrolledAt(enrollmentDto.getEnrolledAt());
        return enrollment;
    }
}