package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.DoctorRequest;
import com.portofolio.clinicapi.dto.DoctorResponse;
import java.util.List;

public interface DoctorService {
    DoctorResponse createDoctor(DoctorRequest doctorRequest);
    List<DoctorResponse> getAllDoctors();
    // Nanti kita akan tambahkan update dan delete
}