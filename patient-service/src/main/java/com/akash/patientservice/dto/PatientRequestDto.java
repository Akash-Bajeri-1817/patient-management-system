package com.akash.patientservice.dto;

import com.akash.patientservice.dto.validations.CreatePatinetValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PatientRequestDto
{
    @NotBlank(message = "Name required ")
    @Size(max = 50, message = "max char allowed is 50, min is 3" , min = 3)
    private String name;

    @NotBlank(message = "Emial is required")
    @Email(message = "valid emial is required")
    private String email;

    @NotBlank(message = "address required")
    private String address;

    @NotBlank(message = "date of birth required")
    private String dateOfBirth;

    @NotBlank(groups = CreatePatinetValidationGroups.class, message = "registration date required")
    private String registrationDate;

    public @NotBlank(message = "Name required ") @Size(max = 50, message = "max char allowed is 50, min is 3", min = 3) String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name required ") @Size(max = 50, message = "max char allowed is 50, min is 3", min = 3) String name) {
        this.name = name;
    }

    public @NotBlank(message = "Emial is required") @Email(message = "valid emial is required") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Emial is required") @Email(message = "valid emial is required") String email) {
        this.email = email;
    }

    public @NotBlank(message = "address required") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "address required") String address) {
        this.address = address;
    }

    public @NotBlank(message = "date of birth required") String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotBlank(message = "date of birth required") String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public  String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
}
