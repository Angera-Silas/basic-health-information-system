package com.angerasilas.health_system_backend.repository;

import com.angerasilas.health_system_backend.dto.ClientInformationDto;
import com.angerasilas.health_system_backend.dto.RecentClients;
import com.angerasilas.health_system_backend.entity.ClientDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long> {

    ClientDetails findByUserId(Long userId);

    @Query("""
            SELECT new com.angerasilas.health_system_backend.dto.RecentClients(
                u.name,
                c.gender,
                c.dateOfBirth
            )
            FROM ClientDetails c
            JOIN c.user u
            ORDER BY c.id DESC
            """)
    List<RecentClients> findRecentClients();

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.RecentClients(
                    u.name,
                    c.gender,
                    c.dateOfBirth
                )
                FROM ClientDetails c
                JOIN c.user u
                LEFT JOIN c.registeredBy d
                WHERE d.id = :doctorId
                ORDER BY c.id DESC
            """)
    List<RecentClients> findRecentClientsByDoctor(Long doctorId);

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
                WHERE u.name LIKE %:name% 
                GROUP BY c.id, u.id, u.name, u.email, u.phone, c.dateOfBirth, c.gender, c.physicalAddress, c.street, c.town, c.active, d.id, du.name, du.email, du.phone
            """)
    List<ClientInformationDto> findClientInformationByName(String name);

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
                WHERE d.id = :doctorId
                GROUP BY c.id, u.id, u.name, u.email, u.phone, c.dateOfBirth, c.gender, c.physicalAddress, c.street, c.town, c.active, d.id, du.name, du.email, du.phone
            """)
    List<ClientInformationDto> findAllClientInformationByDoctorId(Long doctorId);

    @Query("""
                SELECT DISTINCT p.name
                FROM Enrollment e
                JOIN e.program p
                JOIN e.client c
            """)
    List<String> findProgramNamesByClient();

    @Query("SELECT COUNT(c) FROM ClientDetails c WHERE c.registeredBy.id = :doctorId")
    long countClientsByDoctorId(Long doctorId);



    @Query("""
        SELECT p.id, p.name
        FROM ProgramEntity p
        WHERE p.doctorDetails.id = :doctorId
        AND p.id NOT IN (
            SELECT e.program.id
            FROM Enrollment e
            WHERE e.client.id = :clientId
        )
        """)
List<Object[]> findUnenrolledProgramsByClientIdAndDoctorId(Long clientId, Long doctorId);

}