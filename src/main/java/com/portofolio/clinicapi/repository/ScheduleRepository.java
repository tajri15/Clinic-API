package com.portofolio.clinicapi.repository;

import com.portofolio.clinicapi.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // Method custom untuk mencari semua jadwal milik seorang dokter
    List<Schedule> findByDoctorId(Long doctorId);
}