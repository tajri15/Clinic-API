package com.portofolio.clinicapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relasi Many-to-One: Banyak jadwal bisa dimiliki oleh satu dokter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek; // e.g., MONDAY, TUESDAY

    @Column(nullable = false)
    private LocalTime startTime; // e.g., 09:00

    @Column(nullable = false)
    private LocalTime endTime; // e.g., 17:00
}