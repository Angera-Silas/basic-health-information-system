package com.angerasilas.health_system_backend.mapper;

import com.angerasilas.health_system_backend.dto.ClientDetailsDto;
import com.angerasilas.health_system_backend.entity.ClientDetails;

public class ClientDetailsMapper {

    public static ClientDetailsDto toDto(ClientDetails clientDetails) {
        return new ClientDetailsDto(
                clientDetails.getId(),
                clientDetails.getUser().getId(),
                clientDetails.getDateOfBirth(),
                clientDetails.getGender(),
                clientDetails.getPhysicalAddress(),
                clientDetails.getStreet(),
                clientDetails.getTown(),
                clientDetails.isActive(),
                clientDetails.getRegisteredBy() != null ? clientDetails.getRegisteredBy().getId() : null
        );
    }

    public static ClientDetails toEntity(ClientDetailsDto clientDetailsDto) {
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setId(clientDetailsDto.getId());
        clientDetails.setDateOfBirth(clientDetailsDto.getDateOfBirth());
        clientDetails.setGender(clientDetailsDto.getGender());
        clientDetails.setPhysicalAddress(clientDetailsDto.getPhysicalAddress());
        clientDetails.setStreet(clientDetailsDto.getStreet());
        clientDetails.setTown(clientDetailsDto.getTown());
        clientDetails.setActive(clientDetailsDto.isActive());
        return clientDetails;
    }
}