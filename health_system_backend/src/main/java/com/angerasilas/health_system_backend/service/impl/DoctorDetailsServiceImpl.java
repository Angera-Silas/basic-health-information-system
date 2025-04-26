package com.angerasilas.health_system_backend.service.impl;

import com.angerasilas.health_system_backend.dto.DoctorDetailsDto;
import com.angerasilas.health_system_backend.dto.DoctorInformationDto;
import com.angerasilas.health_system_backend.entity.DoctorDetails;
import com.angerasilas.health_system_backend.entity.UserEntity;
import com.angerasilas.health_system_backend.mapper.DoctorDetailsMapper;
import com.angerasilas.health_system_backend.repository.DoctorDetailsRepository;
import com.angerasilas.health_system_backend.repository.UserRepository;
import com.angerasilas.health_system_backend.service.DoctorDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorDetailsServiceImpl implements DoctorDetailsService {

    private final DoctorDetailsRepository doctorDetailsRepository;
    private final UserRepository userRepository;

    public DoctorDetailsServiceImpl(DoctorDetailsRepository doctorDetailsRepository, UserRepository userRepository) {
        this.doctorDetailsRepository = doctorDetailsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<DoctorDetailsDto> getAllDoctorDetails() {
        return doctorDetailsRepository.findAll()
                .stream()
                .map(DoctorDetailsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DoctorDetailsDto getDoctorDetailsById(Long id) {
        DoctorDetails doctorDetails = doctorDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DoctorDetails not found"));
        return DoctorDetailsMapper.toDto(doctorDetails);
    }

    @Override
    public DoctorDetailsDto getDoctorDetailsByUserId(Long userId) {
        DoctorDetails doctorDetails = doctorDetailsRepository.findByUserId(userId);
        if (doctorDetails == null) {
            throw new RuntimeException("DoctorDetails not found for user ID: " + userId);
        }
        return DoctorDetailsMapper.toDto(doctorDetails);
    }

    @Override
    public DoctorDetailsDto createDoctorDetails(DoctorDetailsDto doctorDetailsDto) {
        UserEntity user = userRepository.findById(doctorDetailsDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        DoctorDetails doctorDetails = DoctorDetailsMapper.toEntity(doctorDetailsDto);
        doctorDetails.setUser(user);

        DoctorDetails savedDoctorDetails = doctorDetailsRepository.save(doctorDetails);
        return DoctorDetailsMapper.toDto(savedDoctorDetails);
    }

    @Override
    public DoctorDetailsDto updateDoctorDetails(Long id, DoctorDetailsDto doctorDetailsDto) {
        DoctorDetails doctorDetails = doctorDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DoctorDetails not found"));

        doctorDetails.setSpecialization(doctorDetailsDto.getSpecialization());
        doctorDetails.setDateOfBirth(doctorDetailsDto.getDateOfBirth());
        doctorDetails.setGender(doctorDetailsDto.getGender());
        doctorDetails.setPhysicalAddress(doctorDetailsDto.getPhysicalAddress());
        doctorDetails.setStreet(doctorDetailsDto.getStreet());
        doctorDetails.setTown(doctorDetailsDto.getTown());

        DoctorDetails updatedDoctorDetails = doctorDetailsRepository.save(doctorDetails);
        return DoctorDetailsMapper.toDto(updatedDoctorDetails);
    }

    @Override
    public void deleteDoctorDetails(Long id) {
        doctorDetailsRepository.deleteById(id);
    }



    @Override
    public DoctorInformationDto getDoctorInformationById(Long doctorId) {
        DoctorInformationDto doctorInfo = doctorDetailsRepository.findDoctorInformationWithoutProgramsAndClientsById(doctorId);

        // Fetch programs and clients
        List<String> programs = doctorDetailsRepository.findProgramNamesByDoctorId(doctorId);
        List<String> clients = doctorDetailsRepository.findClientNamesByDoctorId(doctorId);

        // Set programs and clients
        doctorInfo.setPrograms(programs);
        doctorInfo.setClients(clients);

        return doctorInfo;
    }

    @Override
    public List<DoctorInformationDto> getAllDoctorInformation() {
        List<DoctorInformationDto> doctors = doctorDetailsRepository.findAllDoctorInformation();

        // For each doctor, fetch programs and clients
        doctors.forEach(doctor -> {
            List<String> programs = doctorDetailsRepository.findProgramNamesByDoctorId(doctor.getId());
            List<String> clients = doctorDetailsRepository.findClientNamesByDoctorId(doctor.getId());
            doctor.setPrograms(programs);
            doctor.setClients(clients);
        });

        return doctors;
    }
}