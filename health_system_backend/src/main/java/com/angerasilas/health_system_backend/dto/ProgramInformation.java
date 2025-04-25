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
public class ProgramInformation {
    private Long id;
    private String name;
    private String description;
    private String createdAt;
    private Long createdById;
    private String createdByName;
    private String createdByEmail;
    private List<String> clients;
}
