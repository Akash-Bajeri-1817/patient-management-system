package com.akash.patientservice.controller;

import com.akash.patientservice.dto.PatientRequestDto;
import com.akash.patientservice.dto.PatientResponseDto;
import com.akash.patientservice.repository.PatientRepository;
import com.akash.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<PatientResponseDto> createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto)
    {
        PatientResponseDto patientResponseDto = patientService.createPatient(patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDto);
    }
}
