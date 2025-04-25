package com.angerasilas.health_system_backend.repository;

import com.angerasilas.health_system_backend.dto.DoctorInformationDto;
import com.angerasilas.health_system_backend.entity.DoctorDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, Long> {

    @Query("""
        SELECT new com.angerasilas.health_system_backend.dto.DoctorInformationDto(
            d.id,
            u.name,
            u.email,
            u.phone,
            u.id,
            d.specialization,
            d.dateOfBirth,
            d.gender,
            d.physicalAddress,
            d.street,
            d.town,
            null,
            null
        )
        FROM DoctorDetails d
        JOIN d.user u
        WHERE d.id = :doctorId
    """)
    DoctorInformationDto findDoctorInformationWithoutProgramsAndClientsById(Long doctorId);

    @Query("""
        SELECT DISTINCT p.name
        FROM ProgramEntity p
        WHERE p.doctorDetails.id = :doctorId
    """)
    List<String> findProgramNamesByDoctorId(Long doctorId);

    @Query("""
        SELECT DISTINCT c.user.name
        FROM ClientDetails c
        WHERE c.registeredBy.id = :doctorId
    """)
    List<String> findClientNamesByDoctorId(Long doctorId);

    @Query("""
        SELECT new com.angerasilas.health_system_backend.dto.DoctorInformationDto(
            d.id,
            u.name,
            u.email,
            u.phone,
            u.id,
            d.specialization,
            d.dateOfBirth,
            d.gender,
            d.physicalAddress,
            d.street,
            d.town,
            null,
            null
        )
        FROM DoctorDetails d
        JOIN d.user u
    """)
    List<DoctorInformationDto> findAllDoctorInformation();
}