package com.angerasilas.health_system_backend.repository;

import com.angerasilas.health_system_backend.dto.ProgramInformation;
import com.angerasilas.health_system_backend.dto.RecentProgram;
import com.angerasilas.health_system_backend.entity.ProgramEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramEntity, Long> {

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.ProgramInformation(
                    p.id,
                    p.name,
                    p.description,
                    p.createdAt,
                    d.id,
                    d.user.name,
                    d.user.email,
                    null
                )
                FROM ProgramEntity p
                JOIN p.doctorDetails d
                WHERE p.id = :programId
            """)
    ProgramInformation findProgramInformationWithoutClientsById(Long programId);

    @Query("""
                SELECT DISTINCT c.user.name
                FROM Enrollment e
                JOIN e.client c
                WHERE e.program.id = :programId
            """)
    List<String> findClientNamesByProgramId(Long programId);

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.ProgramInformation(
                    p.id,
                    p.name,
                    p.description,
                    p.createdAt,
                    d.id,
                    d.user.name,
                    d.user.email,
                    null
                )
                FROM ProgramEntity p
                JOIN p.doctorDetails d
            """)
    List<ProgramInformation> findAllProgramInformation();

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.ProgramInformation(
                    p.id,
                    p.name,
                    p.description,
                    p.createdAt,
                    d.id,
                    d.user.name,
                    d.user.email,
                    null
                )
                FROM ProgramEntity p
                JOIN p.doctorDetails d
                WHERE d.id = :doctorId
            """)
    List<ProgramInformation> findAllProgramInformationByDoctorId(Long doctorId);

    @Query("""
                SELECT new com.angerasilas.health_system_backend.dto.RecentProgram(
                    p.name,
                    p.description
                )
                FROM ProgramEntity p
                LEFT JOIN p.doctorDetails d
                WHERE d.id = :doctorId
                ORDER BY p.createdAt DESC
            """)
    List<RecentProgram> findRecentProgramsByDoctorId(Long doctorId);

    @Query("""
            SELECT new com.angerasilas.health_system_backend.dto.RecentProgram(
            p.name,
            p.description
            )
            FROM ProgramEntity p
            JOIN p.doctorDetails d
            ORDER BY p.createdAt DESC

            """)
    List<RecentProgram> findAllRecentPrograms();

    @Query("SELECT COUNT(p) FROM ProgramEntity p WHERE p.doctorDetails.id = :doctorId")
    long countProgramsByDoctorId(Long doctorId);

    // New query to find unenrolled clients added by the doctor
    @Query("""
            SELECT c.id, u.name
            FROM ClientDetails c 
            JOIN c.user u 
            WHERE c.registeredBy.id = :doctorId
            AND c.id NOT IN (
                SELECT e.client.id
                FROM Enrollment e
                WHERE e.program.id = :programId
            )
            """)
    List<Object[]> findUnenrolledClientsByDoctorIdAndProgramId(Long doctorId, Long programId);
}