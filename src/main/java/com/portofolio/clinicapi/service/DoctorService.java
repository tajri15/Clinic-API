package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.DoctorRequest;
import com.portofolio.clinicapi.dto.DoctorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {
    DoctorResponse createDoctor(DoctorRequest doctorRequest);
    Page<DoctorResponse> getAllDoctors(Pageable pageable);

    // METHOD BARU: Untuk menghapus dokter
    void deleteDoctor(Long doctorId);
}