package com.angerasilas.health_system_backend.repository;

import com.angerasilas.health_system_backend.dto.ClientInformationDto;
import com.angerasilas.health_system_backend.entity.ClientDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long> {

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.ClientInformationDto(
                    c.id,
                    u.id,
                    u.name,
                    u.email,
                    u.phone,
                    c.dateOfBirth,
                    c.gender,
                    c.physicalAddress,
                    c.street,
                    c.town,
                    c.active,
                    d.id,
                    du.name,
                    du.email,
                    du.phone,
                    null
                )
                FROM ClientDetails c
                JOIN c.user u
                LEFT JOIN c.registeredBy d
                LEFT JOIN d.user du
                LEFT JOIN Enrollment e ON e.client.id = c.id
                LEFT JOIN e.program p
                WHERE c.id = :clientId
                GROUP BY c.id, u.id, u.name, u.email, u.phone, c.dateOfBirth, c.gender, c.physicalAddress, c.street, c.town, c.active, d.id, du.name, du.email, du.phone
            """)
    ClientInformationDto findClientInformationWithoutProgramsById(Long clientId);

    @Query("""
                SELECT DISTINCT p.name
                FROM Enrollment e
                JOIN e.program p
                WHERE e.client.id = :clientId
            """)
    List<String> findProgramNamesByClientId(Long clientId);

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.ClientInformationDto(
                    c.id,
                    u.id,
                    u.name,
                    u.email,
                    u.phone,
                    c.dateOfBirth,
                    c.gender,
                    c.physicalAddress,
                    c.street,
                    c.town,
                    c.active,
                    d.id,
                    du.name,
                    du.email,
                    du.phone,
                    null
                )
                FROM ClientDetails c
                JOIN c.user u
                LEFT JOIN c.registeredBy d
                LEFT JOIN d.user du
                LEFT JOIN Enrollment e ON e.client.id = c.id
                LEFT JOIN e.program p
                GROUP BY c.id, u.id, u.name, u.email, u.phone, c.dateOfBirth, c.gender, c.physicalAddress, c.street, c.town, c.active, d.id, du.name, du.email, du.phone
            """)
    List<ClientInformationDto> findAllClientInformation();

    @Query("""
                SELECT DISTINCT p.name
                FROM Enrollment e
                JOIN e.program p
                JOIN e.client c
            """)
    List<String> findProgramNamesByClient();

}