package com.portofolio.clinicapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequest {
    @NotBlank(message = "Doctor name cannot be blank")
    private String name;

    @NotBlank(message = "Specialization cannot be blank")
    private String specialization;
}