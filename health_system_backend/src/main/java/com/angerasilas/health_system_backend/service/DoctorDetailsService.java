package com.angerasilas.health_system_backend.service;

import com.angerasilas.health_system_backend.dto.DoctorDetailsDto;
import com.angerasilas.health_system_backend.dto.DoctorInformationDto;

import java.util.List;

public interface DoctorDetailsService {
    List<DoctorDetailsDto> getAllDoctorDetails();
    DoctorDetailsDto getDoctorDetailsById(Long id);
    DoctorDetailsDto getDoctorDetailsByUserId(Long userId);
    DoctorDetailsDto createDoctorDetails(DoctorDetailsDto doctorDetailsDto);
    DoctorDetailsDto updateDoctorDetails(Long id, DoctorDetailsDto doctorDetailsDto);
    void deleteDoctorDetails(Long id);
    List<DoctorInformationDto> getAllDoctorInformation();
    DoctorInformationDto getDoctorInformationById(Long doctorId);

}