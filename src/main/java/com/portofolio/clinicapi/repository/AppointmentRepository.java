package com.portofolio.clinicapi.repository;

import com.portofolio.clinicapi.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Cek apakah sudah ada appointment untuk dokter di waktu yang sama
    boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);

    // Cari semua appointment milik seorang pasien
    List<Appointment> findByPatientId(Long patientId);
}