package com.akash.patientservice.service;

import com.akash.patientservice.dto.PatientRequestDto;
import com.akash.patientservice.dto.PatientResponseDto;
import com.akash.patientservice.exception.EmailAlreadyExitsException;
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

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto)
    {
        if(patientRepository.existsByEmail(patientRequestDto.getEmail()))
        {
            throw new EmailAlreadyExitsException("Email already exits for user " + patientRequestDto.getEmail());
        }
        Patient patient = patientRepository.save(PatientMapper.toEntity(patientRequestDto));

        return PatientMapper.toDto(patient);
    }
}
