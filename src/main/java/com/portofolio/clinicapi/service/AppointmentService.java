package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.AppointmentRequest;
import com.portofolio.clinicapi.dto.AppointmentResponse;
import java.util.List;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest);
    List<AppointmentResponse> getMyAppointments();
}