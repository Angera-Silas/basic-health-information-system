package com.angerasilas.health_system_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDto {
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private Long createdById;
}