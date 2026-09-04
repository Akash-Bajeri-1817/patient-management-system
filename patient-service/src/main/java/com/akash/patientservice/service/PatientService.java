package com.akash.patientservice.service;

import com.akash.patientservice.dto.PatientResponseDto;
import com.akash.patientservice.mapper.PatientMapper;
import com.akash.patientservice.model.Patient;
import com.akash.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PatientService
{
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository)
    {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatients()
    {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDto> patientResponseDto = patients.stream()
                .map(PatientMapper::toDto).toList();
        return patientResponseDto;
    }
}
