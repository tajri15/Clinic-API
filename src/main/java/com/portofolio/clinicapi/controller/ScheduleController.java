package com.portofolio.clinicapi.controller;

import com.portofolio.clinicapi.dto.ScheduleRequest;
import com.portofolio.clinicapi.dto.ScheduleResponse;
import com.portofolio.clinicapi.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/schedules")
@PreAuthorize("hasRole('ADMIN')")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        ScheduleResponse newSchedule = scheduleService.createSchedule(scheduleRequest);
        return new ResponseEntity<>(newSchedule, HttpStatus.CREATED);
    }
}