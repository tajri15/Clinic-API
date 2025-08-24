package com.portofolio.clinicapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoctorResponse {
    private Long id;
    private String name;
    private String specialization;
}