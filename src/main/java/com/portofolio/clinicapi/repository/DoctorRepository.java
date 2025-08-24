package com.portofolio.clinicapi.repository;

import com.portofolio.clinicapi.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}