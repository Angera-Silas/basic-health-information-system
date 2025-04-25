package com.angerasilas.health_system_backend.controller;

import com.angerasilas.health_system_backend.dto.ProgramDto;
import com.angerasilas.health_system_backend.dto.ProgramInformation;
import com.angerasilas.health_system_backend.service.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public ResponseEntity<List<ProgramDto>> getAllPrograms() {
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDto> getProgramById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ProgramDto> createProgram(@RequestBody ProgramDto programDto) {
        return ResponseEntity.ok(programService.createProgram(programDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable Long id, @RequestBody ProgramDto programDto) {
        return ResponseEntity.ok(programService.updateProgram(id, programDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/information/{id}")
    public ResponseEntity<ProgramInformation> getProgramInformationById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgramInformationById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProgramInformation>> getAllProgramInformation() {
        return ResponseEntity.ok(programService.getAllProgramInformation());
    }
}