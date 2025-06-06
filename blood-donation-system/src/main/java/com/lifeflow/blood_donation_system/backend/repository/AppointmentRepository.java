package com.lifeflow.blood_donation_system.backend.repository;

import com.lifeflow.blood_donation_system.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByDonor_id(int donorId);
}
