package com.portofolio.clinicapi.repository;

import com.portofolio.clinicapi.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);
    List<Appointment> findByPatientId(Long patientId);

    // METHOD BARU: Untuk mencari semua janji temu yang terkait dengan seorang dokter
    List<Appointment> findByDoctorId(Long doctorId);
}