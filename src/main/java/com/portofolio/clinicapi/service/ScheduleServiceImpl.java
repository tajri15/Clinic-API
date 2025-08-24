package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.ScheduleRequest;
import com.portofolio.clinicapi.dto.ScheduleResponse;
import com.portofolio.clinicapi.exception.ResourceNotFoundException; // DIUBAH: Import exception baru
import com.portofolio.clinicapi.model.Doctor;
import com.portofolio.clinicapi.model.Schedule;
import com.portofolio.clinicapi.repository.DoctorRepository;
import com.portofolio.clinicapi.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest) {
        // 1. Cari dokter berdasarkan ID
        Doctor doctor = doctorRepository.findById(scheduleRequest.getDoctorId())
                // DIUBAH: Gunakan ResourceNotFoundException
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + scheduleRequest.getDoctorId()));

        // 2. Buat objek schedule baru
        Schedule schedule = new Schedule();
        schedule.setDoctor(doctor);
        schedule.setDayOfWeek(scheduleRequest.getDayOfWeek());
        schedule.setStartTime(scheduleRequest.getStartTime());
        schedule.setEndTime(scheduleRequest.getEndTime());

        // 3. Simpan ke database
        Schedule savedSchedule = scheduleRepository.save(schedule);

        // 4. Kembalikan sebagai DTO
        return mapToScheduleResponse(savedSchedule);
    }

    @Override
    public List<ScheduleResponse> getSchedulesByDoctorId(Long doctorId) {
        // Cek apakah dokter ada
        if (!doctorRepository.existsById(doctorId)) {
            // DIUBAH: Gunakan ResourceNotFoundException
            throw new ResourceNotFoundException("Doctor not found with id: " + doctorId);
        }
        return scheduleRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToScheduleResponse)
                .collect(Collectors.toList());
    }

    private ScheduleResponse mapToScheduleResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .doctorId(schedule.getDoctor().getId())
                .doctorName(schedule.getDoctor().getName())
                .dayOfWeek(schedule.getDayOfWeek())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .build();
    }
}