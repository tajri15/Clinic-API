package com.portofolio.clinicapi.controller;

import com.portofolio.clinicapi.dto.AdminStatsDTO;
import com.portofolio.clinicapi.model.Role;
import com.portofolio.clinicapi.repository.AppointmentRepository;
import com.portofolio.clinicapi.repository.DoctorRepository;
import com.portofolio.clinicapi.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public AdminDashboardController(UserRepository userRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<AdminStatsDTO> getDashboardStats() {
        long totalPatients = userRepository.countByRole(Role.ROLE_PATIENT);
        long totalDoctors = doctorRepository.count();
        long totalAppointments = appointmentRepository.count();

        AdminStatsDTO stats = AdminStatsDTO.builder()
                .totalPatients(totalPatients)
                .totalDoctors(totalDoctors)
                .totalAppointments(totalAppointments)
                .build();

        return ResponseEntity.ok(stats);
    }
}