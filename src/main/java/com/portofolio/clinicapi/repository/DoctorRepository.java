package com.portofolio.clinicapi.repository;

import com.portofolio.clinicapi.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // JpaRepository includes save(), findAll(), and all other CRUD methods
    // It also includes paging and sorting capabilities
}