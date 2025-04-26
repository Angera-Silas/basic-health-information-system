package com.angerasilas.health_system_backend.controller;

import com.angerasilas.health_system_backend.dto.EnrollmentDto;
import com.angerasilas.health_system_backend.dto.EnrollmentInformationDto;
import com.angerasilas.health_system_backend.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDto> getEnrollmentById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EnrollmentDto> createEnrollment(@RequestBody EnrollmentDto enrollmentDto) {
        return ResponseEntity.ok(enrollmentService.createEnrollment(enrollmentDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EnrollmentDto> updateEnrollment(@PathVariable Long id, @RequestBody EnrollmentDto enrollmentDto) {
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, enrollmentDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/information/{id}")
    public ResponseEntity<EnrollmentInformationDto> getEnrollmentInformationById(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentInformationById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<EnrollmentInformationDto>> getAllEnrollmentInformation() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollmentInformation());
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<List<EnrollmentInformationDto>> getEnrollmentInformationByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentInformationByClientId(clientId));
    }

    @GetMapping("/by-program/{programId}")
    public ResponseEntity<List<EnrollmentInformationDto>> getEnrollmentInformationByProgramId(@PathVariable Long programId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentInformationByProgramId(programId));
    }

    @GetMapping("/by-doctor/{doctorId}")
    public ResponseEntity<List<EnrollmentInformationDto>> getEnrollmentInformationByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentInformationByDoctorId(doctorId));
    }
    
}