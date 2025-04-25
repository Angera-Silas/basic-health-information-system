package com.angerasilas.health_system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDetailsDto {
    private Long id;
    private Long userId;
    private String dateOfBirth;
    private String gender;
    private String physicalAddress;
    private String street;
    private String town;
    private boolean active;
    private Long registeredById;
}