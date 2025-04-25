package com.angerasilas.health_system_backend.repository;

import com.angerasilas.health_system_backend.dto.EnrollmentInformationDto;
import com.angerasilas.health_system_backend.entity.Enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.EnrollmentInformationDto(
                    e.id,
                    c.id,
                    p.id,
                    e.startDate,
                    e.endDate,
                    e.status,
                    e.progress,
                    e.notes,
                    e.enrolledAt,
                    d.id,
                    d.user.name,
                    d.user.email,
                    d.user.phone,
                    d.specialization,
                    c.user.name,
                    c.user.email,
                    c.user.phone,
                    c.dateOfBirth,
                    c.gender,
                    p.name,
                    p.description,
                    p.createdAt
                )
                FROM Enrollment e
                JOIN e.client c
                JOIN e.program p
                JOIN p.doctorDetails d
                WHERE e.id = :enrollmentId
            """)
    EnrollmentInformationDto findEnrollmentInformationById(Long enrollmentId);

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.EnrollmentInformationDto(
                    e.id,
                    c.id,
                    p.id,
                    e.startDate,
                    e.endDate,
                    e.status,
                    e.progress,
                    e.notes,
                    e.enrolledAt,
                    d.id,
                    d.user.name,
                    d.user.email,
                    d.user.phone,
                    d.specialization,
                    c.user.name,
                    c.user.email,
                    c.user.phone,
                    c.dateOfBirth,
                    c.gender,
                    p.name,
                    p.description,
                    p.createdAt
                )
                FROM Enrollment e
                JOIN e.client c
                JOIN e.program p
                JOIN p.doctorDetails d
            """)
    List<EnrollmentInformationDto> findAllEnrollmentInformation();

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.EnrollmentInformationDto(
                    e.id,
                    c.id,
                    p.id,
                    e.startDate,
                    e.endDate,
                    e.status,
                    e.progress,
                    e.notes,
                    e.enrolledAt,
                    d.id,
                    d.user.name,
                    d.user.email,
                    d.user.phone,
                    d.specialization,
                    c.user.name,
                    c.user.email,
                    c.user.phone,
                    c.dateOfBirth,
                    c.gender,
                    p.name,
                    p.description,
                    p.createdAt
                )
                FROM Enrollment e
                JOIN e.client c
                JOIN e.program p
                JOIN p.doctorDetails d
                WHERE c.id = :clientId
            """)
    List<EnrollmentInformationDto> findEnrollmentInformationByClientId(Long clientId);

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.EnrollmentInformationDto(
                    e.id,
                    c.id,
                    p.id,
                    e.startDate,
                    e.endDate,
                    e.status,
                    e.progress,
                    e.notes,
                    e.enrolledAt,
                    d.id,
                    d.user.name,
                    d.user.email,
                    d.user.phone,
                    d.specialization,
                    c.user.name,
                    c.user.email,
                    c.user.phone,
                    c.dateOfBirth,
                    c.gender,
                    p.name,
                    p.description,
                    p.createdAt
                )
                FROM Enrollment e
                JOIN e.client c
                JOIN e.program p
                JOIN p.doctorDetails d
                WHERE p.id = :programId
            """)
    List<EnrollmentInformationDto> findEnrollmentInformationByProgramId(Long programId);

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.EnrollmentInformationDto(
                    e.id,
                    c.id,
                    p.id,
                    e.startDate,
                    e.endDate,
                    e.status,
                    e.progress,
                    e.notes,
                    e.enrolledAt,
                    d.id,
                    d.user.name,
                    d.user.email,
                    d.user.phone,
                    d.specialization,
                    c.user.name,
                    c.user.email,
                    c.user.phone,
                    c.dateOfBirth,
                    c.gender,
                    p.name,
                    p.description,
                    p.createdAt
                )
                FROM Enrollment e
                JOIN e.client c
                JOIN e.program p
                JOIN p.doctorDetails d
                WHERE d.id = :doctorId
            """)
    List<EnrollmentInformationDto> findEnrollmentInformationByDoctorId(Long doctorId);
}