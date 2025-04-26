package com.angerasilas.health_system_backend.controller;

import com.angerasilas.health_system_backend.dto.ClientDetailsDto;
import com.angerasilas.health_system_backend.dto.ClientInformationDto;
import com.angerasilas.health_system_backend.service.ClientDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/client-details")
public class ClientDetailsController {

    private final ClientDetailsService clientDetailsService;

    public ClientDetailsController(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDetailsDto>> getAllClientDetails() {
        return ResponseEntity.ok(clientDetailsService.getAllClientDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailsDto> getClientDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(clientDetailsService.getClientDetailsById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ClientDetailsDto> createClientDetails(@RequestBody ClientDetailsDto clientDetailsDto) {
        return ResponseEntity.ok(clientDetailsService.createClientDetails(clientDetailsDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDetailsDto> updateClientDetails(@PathVariable Long id,
            @RequestBody ClientDetailsDto clientDetailsDto) {
        return ResponseEntity.ok(clientDetailsService.updateClientDetails(id, clientDetailsDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClientDetails(@PathVariable Long id) {
        clientDetailsService.deleteClientDetails(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/full-info/{clientId}")
    public ResponseEntity<ClientInformationDto> getClientFullInfo(@PathVariable Long clientId) {
        return ResponseEntity.ok(clientDetailsService.getClientFullInfo(clientId));
    }

    @GetMapping("/full-info")
    public ResponseEntity<List<ClientInformationDto>> getAllClientFullInfo() {
        return ResponseEntity.ok(clientDetailsService.getAllClientFullInfo());
    }

    @GetMapping("/by-doctor/{doctorId}")
    public ResponseEntity<List<ClientInformationDto>> getAllClientInformationByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(clientDetailsService.getAllClientInformationByDoctorId(doctorId));
    }

    @GetMapping("/{clientId}/unenrolled-programs/{doctorId}")
    public ResponseEntity<List<Object>> getUnenrolledPrograms(
            @PathVariable Long clientId,
            @PathVariable Long doctorId) {
        List<Object[]> unenrolledPrograms = clientDetailsService.findUnenrolledProgramsByClientIdAndDoctorId(clientId,
                doctorId);

        // Transform the result into a more readable format (e.g., JSON objects)
        List<Object> response = unenrolledPrograms.stream()
                .map(program -> {
                    Long id = (Long) program[0]; 
                    String name = (String) program[1];
                    return new Object() {
                        public final Long programId = id;
                        public final String programName = name;
                    };
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}