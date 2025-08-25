package com.portofolio.clinicapi.service;

// SEMUA IMPORT YANG HILANG ADA DI SINI
import com.portofolio.clinicapi.dto.AppointmentRequest;
import com.portofolio.clinicapi.dto.AppointmentResponse;
import com.portofolio.clinicapi.model.*;
import com.portofolio.clinicapi.repository.AppointmentRepository;
import com.portofolio.clinicapi.repository.DoctorRepository;
import com.portofolio.clinicapi.repository.ScheduleRepository;
import com.portofolio.clinicapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
// AKHIR DARI BLOK IMPORT

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("patient@test.com");
    }

    @Test
    void createAppointment_whenSlotIsAvailable_shouldSucceed() {
        AppointmentRequest request = new AppointmentRequest();
        request.setDoctorId(1L);
        request.setAppointmentTime(LocalDateTime.of(2025, 12, 22, 10, 0));

        User patient = new User();
        patient.setEmail("patient@test.com");
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        Schedule schedule = new Schedule();
        schedule.setDayOfWeek(DayOfWeek.MONDAY);
        schedule.setStartTime(LocalTime.of(9, 0));
        schedule.setEndTime(LocalTime.of(12, 0));

        when(userRepository.findByEmail("patient@test.com")).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(scheduleRepository.findByDoctorId(1L)).thenReturn(List.of(schedule));
        when(appointmentRepository.existsByDoctorIdAndAppointmentTime(1L, request.getAppointmentTime())).thenReturn(false);
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppointmentResponse response = appointmentService.createAppointment(request);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(AppointmentStatus.BOOKED);
    }

    @Test
    void createAppointment_whenSlotIsNotAvailable_shouldThrowException() {
        AppointmentRequest request = new AppointmentRequest();
        request.setDoctorId(1L);
        request.setAppointmentTime(LocalDateTime.of(2025, 12, 22, 10, 0));

        User patient = new User();
        Doctor doctor = new Doctor();

        when(userRepository.findByEmail("patient@test.com")).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.existsByDoctorIdAndAppointmentTime(1L, request.getAppointmentTime())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            appointmentService.createAppointment(request);
        });
    }
}