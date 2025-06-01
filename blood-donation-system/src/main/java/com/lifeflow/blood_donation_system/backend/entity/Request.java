package com.lifeflow.blood_donation_system.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "Request")

@Data
@NoArgsConstructor
public class Request {

    public enum RequestStatus {
        PENDING, APPROVED, REJECTED
    }

    public enum RequestType {
        URGENT, SCHEDULED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientID", nullable = false)
    private Patient patient;

    @Column(nullable = false, length = 3)
    private String bloodGroup;

    @Column(nullable = false)
    private int quantityNeeded;

    @Column(nullable = false, updatable = false)
    private LocalDateTime requestDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(length = 255)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestType requestType;
}