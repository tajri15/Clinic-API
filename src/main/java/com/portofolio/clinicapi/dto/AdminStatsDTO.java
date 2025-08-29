package com.portofolio.clinicapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdminStatsDTO {
    private long totalPatients;
    private long totalDoctors;
    private long totalAppointments;
}