package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.ScheduleRequest;
import com.portofolio.clinicapi.dto.ScheduleResponse;
import java.util.List;

public interface ScheduleService {
    ScheduleResponse createSchedule(ScheduleRequest scheduleRequest);
    List<ScheduleResponse> getSchedulesByDoctorId(Long doctorId);
}