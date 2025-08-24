package com.portofolio.clinicapi.controller;

import com.portofolio.clinicapi.dto.DoctorRequest;
import com.portofolio.clinicapi.dto.DoctorResponse;
import com.portofolio.clinicapi.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/doctors")
@PreAuthorize("hasRole('ADMIN')") // <-- KEAJAIBANNYA DI SINI!
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(@Valid @RequestBody DoctorRequest doctorRequest) {
        DoctorResponse newDoctor = doctorService.createDoctor(doctorRequest);
        return new ResponseEntity<>(newDoctor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        List<DoctorResponse> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
}