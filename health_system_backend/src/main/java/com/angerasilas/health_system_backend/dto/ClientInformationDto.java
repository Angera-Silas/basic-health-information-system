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
public class ClientInformationDto {
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String phone;
    private String dateOfBirth;
    private String gender;
    private String physicalAddress;
    private String street;
    private String town;
    private boolean active;
    private Long registeredById;
    private String registeredByName;
    private String registeredByEmail;
    private String registeredByPhone;
    private List<String> programs; // List of program names
}