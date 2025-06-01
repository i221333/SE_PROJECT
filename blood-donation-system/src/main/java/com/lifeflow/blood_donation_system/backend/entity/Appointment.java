package com.lifeflow.blood_donation_system.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Appointment")

@Data
@NoArgsConstructor
public class Appointment {

    public enum AppointmentStatus {
        SCHEDULED, COMPLETED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appointmentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donorID", nullable = false)
    private Donor donor;

    @Column(nullable = false)
    private LocalDateTime appointmentDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;
}