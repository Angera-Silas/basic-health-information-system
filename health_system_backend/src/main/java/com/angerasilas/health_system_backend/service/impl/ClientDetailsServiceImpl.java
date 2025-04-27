package com.angerasilas.health_system_backend.service.impl;

import com.angerasilas.health_system_backend.dto.ClientDetailsDto;
import com.angerasilas.health_system_backend.dto.ClientInformationDto;
import com.angerasilas.health_system_backend.entity.ClientDetails;
import com.angerasilas.health_system_backend.entity.DoctorDetails;
import com.angerasilas.health_system_backend.entity.UserEntity;
import com.angerasilas.health_system_backend.mapper.ClientDetailsMapper;
import com.angerasilas.health_system_backend.repository.ClientDetailsRepository;
import com.angerasilas.health_system_backend.repository.DoctorDetailsRepository;
import com.angerasilas.health_system_backend.repository.UserRepository;
import com.angerasilas.health_system_backend.service.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final ClientDetailsRepository clientDetailsRepository;
    private final UserRepository userRepository;
    private final DoctorDetailsRepository doctorDetailsRepository;

    public ClientDetailsServiceImpl(ClientDetailsRepository clientDetailsRepository, UserRepository userRepository,
            DoctorDetailsRepository doctorDetailsRepository) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.userRepository = userRepository;
        this.doctorDetailsRepository = doctorDetailsRepository;
    }

    @Override
    public List<ClientDetailsDto> getAllClientDetails() {
        return clientDetailsRepository.findAll()
                .stream()
                .map(ClientDetailsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDetailsDto getClientDetailsById(Long id) {
        ClientDetails clientDetails = clientDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClientDetails not found"));
        return ClientDetailsMapper.toDto(clientDetails);
    }

    @Override
    public ClientDetailsDto createClientDetails(ClientDetailsDto clientDetailsDto) {
        UserEntity user = userRepository.findById(clientDetailsDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        DoctorDetails registeredBy = null;
        if (clientDetailsDto.getRegisteredById() != null) {
            registeredBy = doctorDetailsRepository.findById(clientDetailsDto.getRegisteredById())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
        }

        ClientDetails clientDetails = ClientDetailsMapper.toEntity(clientDetailsDto);
        clientDetails.setUser(user);
        clientDetails.setRegisteredBy(registeredBy);

        ClientDetails savedClientDetails = clientDetailsRepository.save(clientDetails);
        return ClientDetailsMapper.toDto(savedClientDetails);
    }

    @Override
    public ClientDetailsDto updateClientDetails(Long id, ClientDetailsDto clientDetailsDto) {
        ClientDetails clientDetails = clientDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClientDetails not found"));

        clientDetails.setDateOfBirth(clientDetailsDto.getDateOfBirth());
        clientDetails.setGender(clientDetailsDto.getGender());
        clientDetails.setPhysicalAddress(clientDetailsDto.getPhysicalAddress());
        clientDetails.setStreet(clientDetailsDto.getStreet());
        clientDetails.setTown(clientDetailsDto.getTown());
        clientDetails.setActive(clientDetailsDto.isActive());

        ClientDetails updatedClientDetails = clientDetailsRepository.save(clientDetails);
        return ClientDetailsMapper.toDto(updatedClientDetails);
    }

    @Override
    public void deleteClientDetails(Long id) {
        clientDetailsRepository.deleteById(id);
    }

    @Override
    public ClientInformationDto getClientFullInfo(Long clientId) {
        ClientInformationDto dto = clientDetailsRepository.findClientInformationWithoutProgramsById(clientId);
        List<String> programs = clientDetailsRepository.findProgramNamesByClientId(clientId);
        dto.setPrograms(programs);
        return dto;
    }

    @Override
    public List<ClientInformationDto> getAllClientFullInfo() {
        // Fetch all clients without programs
        List<ClientInformationDto> clients = clientDetailsRepository.findAllClientInformation();

        // For each client, fetch their list of program names and set it
        clients.forEach(client -> {
            List<String> programNames = clientDetailsRepository.findProgramNamesByClientId(client.getId());
            client.setPrograms(programNames);
        });

        return clients;
    }

    @Override
    public List<ClientInformationDto> getAllClientInformationByDoctorId(Long doctorId) {
        // Fetch all clients registered by the doctor
        List<ClientInformationDto> clients = clientDetailsRepository.findAllClientInformationByDoctorId(doctorId);

        // For each client, fetch their list of program names and set it
        clients.forEach(client -> {
            List<String> programNames = clientDetailsRepository.findProgramNamesByClientId(client.getId());
            client.setPrograms(programNames);
        });

        return clients;
    }

    @Override
    public List<Object[]> findUnenrolledProgramsByClientIdAndDoctorId(Long clientId, Long doctorId) {
        return clientDetailsRepository.findUnenrolledProgramsByClientIdAndDoctorId(clientId, doctorId);
    }

    @Override
    public List<ClientInformationDto> getClientInformationByName(String name) {
        // Fetch all clients by name
        List<ClientInformationDto> clients = clientDetailsRepository.findClientInformationByName(name);

        // For each client, fetch their list of program names and set it
        clients.forEach(client -> {
            List<String> programNames = clientDetailsRepository.findProgramNamesByClientId(client.getId());
            client.setPrograms(programNames);
        });

        return clients;
    }

}