package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.DoctorRequest;
import com.portofolio.clinicapi.dto.DoctorResponse;
import com.portofolio.clinicapi.model.Doctor;
import com.portofolio.clinicapi.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorResponse createDoctor(DoctorRequest doctorRequest) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorRequest.getName());
        doctor.setSpecialization(doctorRequest.getSpecialization());

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToDoctorResponse(savedDoctor);
    }

    @Override
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    private DoctorResponse mapToDoctorResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .build();
    }
}