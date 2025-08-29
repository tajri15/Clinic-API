package com.portofolio.clinicapi.controller;

import com.portofolio.clinicapi.dto.DoctorResponse;
import com.portofolio.clinicapi.dto.ScheduleResponse;
import com.portofolio.clinicapi.service.DoctorService;
import com.portofolio.clinicapi.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ScheduleService scheduleService;

    public DoctorController(DoctorService doctorService, ScheduleService scheduleService) {
        this.doctorService = doctorService;
        this.scheduleService = scheduleService;
    }

    // Endpoint ini sekarang menerima parameter Pageable
    @GetMapping
    public ResponseEntity<Page<DoctorResponse>> getAllDoctors(Pageable pageable) {
        Page<DoctorResponse> doctors = doctorService.getAllDoctors(pageable);
        return ResponseEntity.ok(doctors);
    }

    @GetMapping("/{doctorId}/schedules")
    public ResponseEntity<List<ScheduleResponse>> getSchedulesByDoctorId(@PathVariable Long doctorId) {
        List<ScheduleResponse> schedules = scheduleService.getSchedulesByDoctorId(doctorId);
        return ResponseEntity.ok(schedules);
    }
}