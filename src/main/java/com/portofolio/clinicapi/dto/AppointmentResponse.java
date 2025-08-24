package com.portofolio.clinicapi.dto;

import com.portofolio.clinicapi.model.AppointmentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AppointmentResponse {
    private Long id;
    private String patientName;
    private String doctorName;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
}