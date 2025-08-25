package com.portofolio.clinicapi.controller;

// SEMUA IMPORT YANG HILANG ADA DI SINI
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portofolio.clinicapi.dto.AppointmentRequest;
import com.portofolio.clinicapi.model.*;
import com.portofolio.clinicapi.repository.DoctorRepository;
import com.portofolio.clinicapi.repository.ScheduleRepository;
import com.portofolio.clinicapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// AKHIR DARI BLOK IMPORT

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    private User testPatient;
    private Doctor testDoctor;

    @BeforeEach
    void setUpDatabase() {
        testPatient = new User();
        testPatient.setEmail("patient@test.com");
        testPatient.setName("Test Patient");
        testPatient.setPassword("password");
        testPatient.setRole(Role.ROLE_PATIENT);
        userRepository.save(testPatient);

        testDoctor = new Doctor();
        testDoctor.setName("Dr. Test");
        testDoctor.setSpecialization("Testing");
        doctorRepository.save(testDoctor);

        Schedule schedule = new Schedule();
        schedule.setDoctor(testDoctor);
        schedule.setDayOfWeek(DayOfWeek.MONDAY);
        schedule.setStartTime(LocalTime.of(9, 0));
        schedule.setEndTime(LocalTime.of(17, 0));
        scheduleRepository.save(schedule);
    }

    @Test
    @WithMockUser(username = "patient@test.com", roles = "PATIENT")
    void whenPatientBooksAppointment_withValidData_shouldReturn201Created() throws Exception {
        AppointmentRequest request = new AppointmentRequest();
        request.setDoctorId(testDoctor.getId());
        request.setAppointmentTime(LocalDateTime.of(2025, 12, 22, 14, 0));

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patientName").value("Test Patient"))
                .andExpect(jsonPath("$.doctorName").value("Dr. Test"))
                .andExpect(jsonPath("$.status").value("BOOKED"));
    }
}