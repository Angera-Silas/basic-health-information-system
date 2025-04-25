package com.angerasilas.health_system_backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInformationDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Long userId;
    private String specialization;
    private String dateOfBirth;
    private String gender;
    private String physicalAddress;
    private String street;
    private String town;
    private List<String> programs;
    private List<String> clients;
}
