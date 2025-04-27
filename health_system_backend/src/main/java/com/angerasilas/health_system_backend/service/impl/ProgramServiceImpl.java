package com.angerasilas.health_system_backend.service.impl;

import com.angerasilas.health_system_backend.dto.ProgramDto;
import com.angerasilas.health_system_backend.dto.ProgramInformation;
import com.angerasilas.health_system_backend.entity.DoctorDetails;
import com.angerasilas.health_system_backend.entity.ProgramEntity;
import com.angerasilas.health_system_backend.mapper.ProgramMapper;
import com.angerasilas.health_system_backend.repository.DoctorDetailsRepository;
import com.angerasilas.health_system_backend.repository.ProgramRepository;
import com.angerasilas.health_system_backend.service.ProgramService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final DoctorDetailsRepository doctorDetailsRepository;

   
    public ProgramServiceImpl(ProgramRepository programRepository, DoctorDetailsRepository doctorDetailsRepository) {
        this.programRepository = programRepository;
        this.doctorDetailsRepository = doctorDetailsRepository;
    }

    @Override
    public List<ProgramDto> getAllPrograms() {
        return programRepository.findAll()
                .stream()
                .map(ProgramMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramDto getProgramById(Long id) {
        ProgramEntity programEntity = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));
        return ProgramMapper.toDto(programEntity);
    }

    @Override
    public ProgramDto createProgram(ProgramDto programDto) {
        DoctorDetails doctorDetails = doctorDetailsRepository.findById(programDto.getCreatedById())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        ProgramEntity programEntity = ProgramMapper.toEntity(programDto);
        programEntity.setDoctorDetails(doctorDetails);

        ProgramEntity savedProgram = programRepository.save(programEntity);
        return ProgramMapper.toDto(savedProgram);
    }

    @Override
    public ProgramDto updateProgram(Long id, ProgramDto programDto) {
        ProgramEntity programEntity = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        programEntity.setName(programDto.getName());
        programEntity.setDescription(programDto.getDescription());
        programEntity.setCreatedAt(programDto.getCreatedAt());

        ProgramEntity updatedProgram = programRepository.save(programEntity);
        return ProgramMapper.toDto(updatedProgram);
    }

    @Override
    public void deleteProgram(Long id) {
        programRepository.deleteById(id);
    }

    @Override
    public ProgramInformation getProgramInformationById(Long programId) {
        ProgramInformation programInfo = programRepository.findProgramInformationWithoutClientsById(programId);

        // Fetch clients
        List<String> clients = programRepository.findClientNamesByProgramId(programId);

        // Set clients
        programInfo.setClients(clients);

        return programInfo;
    }

    @Override
    public List<ProgramInformation> getAllProgramInformation() {
        List<ProgramInformation> programs = programRepository.findAllProgramInformation();

        // For each program, fetch clients
        programs.forEach(program -> {
            List<String> clients = programRepository.findClientNamesByProgramId(program.getId());
            program.setClients(clients);
        });

        return programs;
    }

    @Override
    public List<ProgramInformation> getProgramsByDoctorId(Long doctorId) {
        return programRepository.findAllProgramInformationByDoctorId(doctorId);
    }

    @Override
    public List<Object[]> findUnenrolledClientsByDoctorIdAndProgramId(Long doctorId, Long programId) {
        return programRepository.findUnenrolledClientsByDoctorIdAndProgramId(doctorId, programId);
    }
}