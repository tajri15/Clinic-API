package com.portofolio.clinicapi.controller;

import com.portofolio.clinicapi.dto.AppointmentRequest;
import com.portofolio.clinicapi.dto.AppointmentResponse;
import com.portofolio.clinicapi.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@PreAuthorize("hasRole('PATIENT')") // HANYA PASIEN YANG BISA AKSES INI
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        AppointmentResponse newAppointment = appointmentService.createAppointment(appointmentRequest);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments() {
        List<AppointmentResponse> myAppointments = appointmentService.getMyAppointments();
        return ResponseEntity.ok(myAppointments);
    }
}