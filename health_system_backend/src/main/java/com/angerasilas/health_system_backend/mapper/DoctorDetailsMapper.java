package com.angerasilas.health_system_backend.mapper;

import com.angerasilas.health_system_backend.dto.DoctorDetailsDto;
import com.angerasilas.health_system_backend.entity.DoctorDetails;

public class DoctorDetailsMapper {

    public static DoctorDetailsDto toDto(DoctorDetails doctorDetails) {
        return new DoctorDetailsDto(
                doctorDetails.getId(),
                doctorDetails.getUser().getId(),
                doctorDetails.getSpecialization(),
                doctorDetails.getDateOfBirth(),
                doctorDetails.getGender(),
                doctorDetails.getPhysicalAddress(),
                doctorDetails.getStreet(),
                doctorDetails.getTown()
        );
    }

    public static DoctorDetails toEntity(DoctorDetailsDto doctorDetailsDto) {
        DoctorDetails doctorDetails = new DoctorDetails();
        doctorDetails.setId(doctorDetailsDto.getId());
        doctorDetails.setSpecialization(doctorDetailsDto.getSpecialization());
        doctorDetails.setDateOfBirth(doctorDetailsDto.getDateOfBirth());
        doctorDetails.setGender(doctorDetailsDto.getGender());
        doctorDetails.setPhysicalAddress(doctorDetailsDto.getPhysicalAddress());
        doctorDetails.setStreet(doctorDetailsDto.getStreet());
        doctorDetails.setTown(doctorDetailsDto.getTown());
        return doctorDetails;
    }
}