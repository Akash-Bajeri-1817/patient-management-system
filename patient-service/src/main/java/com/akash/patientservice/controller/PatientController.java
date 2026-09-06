package com.akash.patientservice.controller;

import com.akash.patientservice.dto.PatientRequestDto;
import com.akash.patientservice.dto.PatientResponseDto;
import com.akash.patientservice.dto.validations.CreatePatinetValidationGroups;
import com.akash.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "API for patient management")
public class PatientController
{
    private final PatientService patientService;

    public PatientController(PatientService patientService)
    {
        this.patientService = patientService;
    }

    @Operation(summary = "Get all patients", description = "Retrieves a list of all registered patients")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved patient list",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PatientResponseDto.class)))
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PatientResponseDto>> getPatients()
    {
        List<PatientResponseDto> patientResponseDtos = patientService.getPatients();
        return ResponseEntity.ok().body(patientResponseDtos);
    }

    @Operation(summary = "Create a new patient", description = "Registers a new patient with required default and creation-specific validation rules")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Patient successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Validation failed for request body", content = @Content),
            @ApiResponse(responseCode = "409", description = "Patient with given email already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PatientResponseDto> createPatient(@Validated({Default.class, CreatePatinetValidationGroups.class}) @RequestBody PatientRequestDto patientRequestDto)
    {
        PatientResponseDto patientResponseDto = patientService.createPatient(patientRequestDto);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    @Operation(summary = "Update an existing patient", description = "Updates an existing patient's details by their unique UUID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Patient successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientResponseDto.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplier or request validation failed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found with the provided ID", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email already in use by another patient", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto,
            @Parameter(description = "Unique identifier of the patient", required = true) @PathVariable UUID id)
    {
        PatientResponseDto patientResponseDto = patientService.updatePatient(patientRequestDto, id);
        return ResponseEntity.ok().body(patientResponseDto);
    }

    @Operation(summary = "Delete a patient", description = "Removes a patient record from the system using their unique UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Patient successfully deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found with the provided ID", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @Parameter(description = "Unique identifier of the patient to delete", required = true) @PathVariable UUID id)
    {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}