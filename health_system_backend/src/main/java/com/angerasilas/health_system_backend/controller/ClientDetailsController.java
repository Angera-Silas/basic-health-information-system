package com.angerasilas.health_system_backend.controller;

import com.angerasilas.health_system_backend.dto.ClientDetailsDto;
import com.angerasilas.health_system_backend.dto.ClientInformationDto;
import com.angerasilas.health_system_backend.service.ClientDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ClientDetailsDto> updateClientDetails(@PathVariable Long id, @RequestBody ClientDetailsDto clientDetailsDto) {
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
}