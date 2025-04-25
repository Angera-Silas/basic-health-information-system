package com.angerasilas.health_system_backend.service;

import com.angerasilas.health_system_backend.dto.ClientDetailsDto;
import com.angerasilas.health_system_backend.dto.ClientInformationDto;

import java.util.List;

public interface ClientDetailsService {
    List<ClientDetailsDto> getAllClientDetails();
    ClientDetailsDto getClientDetailsById(Long id);
    ClientDetailsDto createClientDetails(ClientDetailsDto clientDetailsDto);
    ClientDetailsDto updateClientDetails(Long id, ClientDetailsDto clientDetailsDto);
    void deleteClientDetails(Long id);
    ClientInformationDto getClientFullInfo(Long clientId);
    List<ClientInformationDto> getAllClientFullInfo();
}