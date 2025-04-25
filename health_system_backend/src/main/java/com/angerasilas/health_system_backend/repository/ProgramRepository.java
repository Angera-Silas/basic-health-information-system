package com.angerasilas.health_system_backend.repository;

import com.angerasilas.health_system_backend.dto.ProgramInformation;
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
}