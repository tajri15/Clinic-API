package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.DoctorRequest;
import com.portofolio.clinicapi.dto.DoctorResponse;
import com.portofolio.clinicapi.exception.ResourceNotFoundException;
import com.portofolio.clinicapi.model.Appointment;
import com.portofolio.clinicapi.model.Doctor;
import com.portofolio.clinicapi.model.Schedule;
import com.portofolio.clinicapi.repository.AppointmentRepository;
import com.portofolio.clinicapi.repository.DoctorRepository;
import com.portofolio.clinicapi.repository.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    // Tambahkan repository lain untuk menghapus data terkait
    private final ScheduleRepository scheduleRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, ScheduleRepository scheduleRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
        this.appointmentRepository = appointmentRepository;
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
    public Page<DoctorResponse> getAllDoctors(Pageable pageable) {
        Page<Doctor> doctorsPage = doctorRepository.findAll(pageable);
        return doctorsPage.map(this::mapToDoctorResponse);
    }

    @Override
    @Transactional // Penting untuk operasi multi-database
    public void deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        // 1. Hapus semua jadwal yang terkait dengan dokter ini
        List<Schedule> schedules = scheduleRepository.findByDoctorId(doctorId);
        scheduleRepository.deleteAll(schedules);

        // 2. Hapus semua janji temu yang terkait dengan dokter ini
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        appointmentRepository.deleteAll(appointments);

        // 3. Setelah data terkait bersih, hapus dokternya
        doctorRepository.delete(doctor);
    }

    private DoctorResponse mapToDoctorResponse(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specialization(doctor.getSpecialization())
                .build();
    }
}