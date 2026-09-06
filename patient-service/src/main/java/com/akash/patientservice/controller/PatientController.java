package com.akash.patientservice.controller;

import com.akash.patientservice.dto.PatientRequestDto;
import com.akash.patientservice.dto.PatientResponseDto;
import com.akash.patientservice.dto.validations.CreatePatinetValidationGroups;
import com.akash.patientservice.repository.PatientRepository;
import com.akash.patientservice.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController
{
    private final PatientService patientService;

    public PatientController(PatientService patientService)
    {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients()
    {
        List<PatientResponseDto> patientResponseDtos = patientService.getPatients();
        return ResponseEntity.ok().body(patientResponseDtos);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Validated({Default.class, CreatePatinetValidationGroups.class}) @RequestBody PatientRequestDto patientRequestDto)
    {
        PatientResponseDto patientResponseDto = patientService.createPatient(patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(@Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto, @PathVariable UUID id)
    {
        PatientResponseDto patientResponseDto = patientService.updatePatient(patientRequestDto, id);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id)
    {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
