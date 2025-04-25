package com.angerasilas.health_system_backend.service.impl;

import com.angerasilas.health_system_backend.dto.EnrollmentDto;
import com.angerasilas.health_system_backend.dto.EnrollmentInformationDto;
import com.angerasilas.health_system_backend.entity.ClientDetails;
import com.angerasilas.health_system_backend.entity.DoctorDetails;
import com.angerasilas.health_system_backend.entity.Enrollment;
import com.angerasilas.health_system_backend.entity.ProgramEntity;
import com.angerasilas.health_system_backend.mapper.EnrollmentMapper;
import com.angerasilas.health_system_backend.repository.ClientDetailsRepository;
import com.angerasilas.health_system_backend.repository.DoctorDetailsRepository;
import com.angerasilas.health_system_backend.repository.EnrollmentRepository;
import com.angerasilas.health_system_backend.repository.ProgramRepository;
import com.angerasilas.health_system_backend.service.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final ClientDetailsRepository clientDetailsRepository;
    private final ProgramRepository programRepository;
    private final DoctorDetailsRepository doctorDetailsRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 ClientDetailsRepository clientDetailsRepository,
                                 ProgramRepository programRepository,
                                 DoctorDetailsRepository doctorDetailsRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.clientDetailsRepository = clientDetailsRepository;
        this.programRepository = programRepository;
        this.doctorDetailsRepository = doctorDetailsRepository;
    }

    @Override
    public List<EnrollmentDto> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(EnrollmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentDto getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return EnrollmentMapper.toDto(enrollment);
    }

    @Override
    public EnrollmentDto createEnrollment(EnrollmentDto enrollmentDto) {
        ClientDetails client = clientDetailsRepository.findById(enrollmentDto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        ProgramEntity program = programRepository.findById(enrollmentDto.getProgramId())
                .orElseThrow(() -> new RuntimeException("Program not found"));
        DoctorDetails createdBy = doctorDetailsRepository.findById(enrollmentDto.getEnrolledById())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Enrollment enrollment = EnrollmentMapper.toEntity(enrollmentDto);
        enrollment.setClient(client);
        enrollment.setProgram(program);
        enrollment.setCreatedBy(createdBy);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return EnrollmentMapper.toDto(savedEnrollment);
    }

    @Override
    public EnrollmentDto updateEnrollment(Long id, EnrollmentDto enrollmentDto) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setStartDate(enrollmentDto.getStartDate());
        enrollment.setEndDate(enrollmentDto.getEndDate());
        enrollment.setStatus(enrollmentDto.getStatus());
        enrollment.setProgress(enrollmentDto.getProgress());
        enrollment.setNotes(enrollmentDto.getNotes());
        enrollment.setEnrolledAt(enrollmentDto.getEnrolledAt());

        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        return EnrollmentMapper.toDto(updatedEnrollment);
    }

    @Override
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

     @Override
    public EnrollmentInformationDto getEnrollmentInformationById(Long enrollmentId) {
        return enrollmentRepository.findEnrollmentInformationById(enrollmentId);
    }

    @Override
    public List<EnrollmentInformationDto> getAllEnrollmentInformation() {
        return enrollmentRepository.findAllEnrollmentInformation();
    }

    @Override
    public List<EnrollmentInformationDto> getEnrollmentInformationByClientId(Long clientId) {
        return enrollmentRepository.findEnrollmentInformationByClientId(clientId);
    }

    @Override
    public List<EnrollmentInformationDto> getEnrollmentInformationByProgramId(Long programId) {
        return enrollmentRepository.findEnrollmentInformationByProgramId(programId);
    }

    @Override
    public List<EnrollmentInformationDto> getEnrollmentInformationByDoctorId(Long doctorId) {
        return enrollmentRepository.findEnrollmentInformationByDoctorId(doctorId);
    }
}