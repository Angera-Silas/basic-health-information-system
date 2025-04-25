package com.angerasilas.health_system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {
    private Long id;
    private Long clientId;
    private Long programId;
    private String startDate;
    private String endDate;
    private String status;
    private String progress;
    private String notes;
    private String enrolledAt;
    private Long enrolledById;
}