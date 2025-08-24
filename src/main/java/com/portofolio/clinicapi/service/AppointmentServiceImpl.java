package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.AppointmentRequest;
import com.portofolio.clinicapi.dto.AppointmentResponse;
import com.portofolio.clinicapi.model.*;
import com.portofolio.clinicapi.repository.AppointmentRepository;
import com.portofolio.clinicapi.repository.DoctorRepository;
import com.portofolio.clinicapi.repository.ScheduleRepository;
import com.portofolio.clinicapi.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, UserRepository userRepository, DoctorRepository doctorRepository, ScheduleRepository scheduleRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {
        // 1. Dapatkan user yang sedang login dari konteks keamanan
        String patientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User patient = userRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        // 2. Dapatkan data dokter
        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        // 3. Validasi ketersediaan slot
        if (!isSlotAvailable(appointmentRequest.getDoctorId(), appointmentRequest.getAppointmentTime())) {
            throw new IllegalStateException("The requested appointment slot is not available.");
        }

        // 4. Buat dan simpan appointment baru
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.BOOKED);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return mapToAppointmentResponse(savedAppointment);
    }

    @Override
    public List<AppointmentResponse> getMyAppointments() {
        String patientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User patient = userRepository.findByEmail(patientEmail)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        return appointmentRepository.findByPatientId(patient.getId())
                .stream()
                .map(this::mapToAppointmentResponse)
                .collect(Collectors.toList());
    }

    private boolean isSlotAvailable(Long doctorId, LocalDateTime appointmentTime) {
        // Cek apakah sudah ada appointment di waktu yang sama persis
        if (appointmentRepository.existsByDoctorIdAndAppointmentTime(doctorId, appointmentTime)) {
            return false;
        }

        // Cek apakah waktu appointment ada di dalam jadwal praktek dokter
        List<Schedule> doctorSchedules = scheduleRepository.findByDoctorId(doctorId);
        return doctorSchedules.stream().anyMatch(schedule ->
                appointmentTime.getDayOfWeek() == schedule.getDayOfWeek() &&
                        !appointmentTime.toLocalTime().isBefore(schedule.getStartTime()) &&
                        !appointmentTime.toLocalTime().isAfter(schedule.getEndTime())
        );
    }

    private AppointmentResponse mapToAppointmentResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .id(appointment.getId())
                .patientName(appointment.getPatient().getName())
                .doctorName(appointment.getDoctor().getName())
                .appointmentTime(appointment.getAppointmentTime())
                .status(appointment.getStatus())
                .build();
    }
}