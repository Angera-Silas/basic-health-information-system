package com.angerasilas.health_system_backend.service;

import com.angerasilas.health_system_backend.dto.ProgramDto;
import com.angerasilas.health_system_backend.dto.ProgramInformation;

import java.util.List;

public interface ProgramService {
    List<ProgramDto> getAllPrograms();
    ProgramDto getProgramById(Long id);
    ProgramDto createProgram(ProgramDto programDto);
    ProgramDto updateProgram(Long id, ProgramDto programDto);
    void deleteProgram(Long id);
    ProgramInformation getProgramInformationById(Long programId);
    List<ProgramInformation> getAllProgramInformation();
    List<ProgramInformation> getProgramsByDoctorId(Long doctorId);
    List<Object[]> findUnenrolledClientsByDoctorIdAndProgramId(Long doctorId, Long programId);
}