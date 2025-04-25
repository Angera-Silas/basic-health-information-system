package com.angerasilas.health_system_backend.controller;

import com.angerasilas.health_system_backend.dto.DoctorDetailsDto;
import com.angerasilas.health_system_backend.dto.DoctorInformationDto;
import com.angerasilas.health_system_backend.service.DoctorDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor-details")
public class DoctorDetailsController {

    private final DoctorDetailsService doctorDetailsService;

    public DoctorDetailsController(DoctorDetailsService doctorDetailsService) {
        this.doctorDetailsService = doctorDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorDetailsDto>> getAllDoctorDetails() {
        return ResponseEntity.ok(doctorDetailsService.getAllDoctorDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailsDto> getDoctorDetailsById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorDetailsService.getDoctorDetailsById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<DoctorDetailsDto> createDoctorDetails(@RequestBody DoctorDetailsDto doctorDetailsDto) {
        return ResponseEntity.ok(doctorDetailsService.createDoctorDetails(doctorDetailsDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DoctorDetailsDto> updateDoctorDetails(@PathVariable Long id, @RequestBody DoctorDetailsDto doctorDetailsDto) {
        return ResponseEntity.ok(doctorDetailsService.updateDoctorDetails(id, doctorDetailsDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDoctorDetails(@PathVariable Long id) {
        doctorDetailsService.deleteDoctorDetails(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/information/{id}")
    public ResponseEntity<DoctorInformationDto> getDoctorInformationById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorDetailsService.getDoctorInformationById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DoctorInformationDto>> getAllDoctorInformation() {
        return ResponseEntity.ok(doctorDetailsService.getAllDoctorInformation());
    }
}