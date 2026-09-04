package com.akash.patientservice.controller;

import com.akash.patientservice.dto.PatientResponseDto;
import com.akash.patientservice.repository.PatientRepository;
import com.akash.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
