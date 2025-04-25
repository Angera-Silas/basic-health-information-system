package com.angerasilas.health_system_backend.mapper;

import com.angerasilas.health_system_backend.dto.ProgramDto;
import com.angerasilas.health_system_backend.entity.ProgramEntity;

public class ProgramMapper {

    public static ProgramDto toDto(ProgramEntity programEntity) {
        return new ProgramDto(
                programEntity.getId(),
                programEntity.getName(),
                programEntity.getDescription(),
                programEntity.getCreatedAt(),
                programEntity.getDoctorDetails().getId()
        );
    }

    public static ProgramEntity toEntity(ProgramDto programDto) {
        ProgramEntity programEntity = new ProgramEntity();
        programEntity.setId(programDto.getId());
        programEntity.setName(programDto.getName());
        programEntity.setDescription(programDto.getDescription());
        programEntity.setCreatedAt(programDto.getCreatedAt());
        return programEntity;
    }
}