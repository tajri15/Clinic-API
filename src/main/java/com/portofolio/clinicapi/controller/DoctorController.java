package com.portofolio.clinicapi.controller;

import com.portofolio.clinicapi.dto.DoctorResponse;
import com.portofolio.clinicapi.dto.ScheduleResponse;
import com.portofolio.clinicapi.service.DoctorService;
import com.portofolio.clinicapi.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors") // Path diubah menjadi publik
public class DoctorController {

    private final DoctorService doctorService;
    private final ScheduleService scheduleService;

    public DoctorController(DoctorService doctorService, ScheduleService scheduleService) {
        this.doctorService = doctorService;
        this.scheduleService = scheduleService;
    }

    // Endpoint ini sekarang publik, siapa saja bisa akses
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        List<DoctorResponse> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }

    // Endpoint baru untuk melihat jadwal dokter tertentu, juga publik
    @GetMapping("/{doctorId}/schedules")
    public ResponseEntity<List<ScheduleResponse>> getSchedulesByDoctorId(@PathVariable Long doctorId) {
        List<ScheduleResponse> schedules = scheduleService.getSchedulesByDoctorId(doctorId);
        return ResponseEntity.ok(schedules);
    }
}