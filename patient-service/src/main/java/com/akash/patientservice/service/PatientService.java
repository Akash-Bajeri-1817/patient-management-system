package com.akash.patientservice.service;

import com.akash.patientservice.dto.PatientRequestDto;
import com.akash.patientservice.dto.PatientResponseDto;
import com.akash.patientservice.exception.EmailAlreadyExitsException;
import com.akash.patientservice.exception.PatientNotFoundException;
import com.akash.patientservice.mapper.PatientMapper;
import com.akash.patientservice.model.Patient;
import com.akash.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    public PatientResponseDto updatePatient(PatientRequestDto patientRequestDto, UUID patientId)
    {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("No record found with Id : "+patientId));
        if(patientRepository.existsByEmailAndIdNot(patientRequestDto.getEmail(),  patientId))
        {
            throw new EmailAlreadyExitsException("Email already exits for user " + patientRequestDto.getEmail());
        }

        patient.setName(patientRequestDto.getName());
        patient.setEmail(patientRequestDto.getEmail());
        patient.setAddress(patientRequestDto.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDto.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDto(updatedPatient);
    }

    public void deletePatient(UUID patientId)
    {
        patientRepository.deleteById(patientId);
    }
}
