package com.lifeflow.blood_donation_system.backend.service;

import com.lifeflow.blood_donation_system.backend.entity.Appointment;
import com.lifeflow.blood_donation_system.backend.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public void scheduleAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByDonor(int donorId) {
        return appointmentRepository.findByDonor_id(donorId);
    }
}
