package com.lifeflow.blood_donation_system.backend.service;

import com.lifeflow.blood_donation_system.backend.entity.Patient;
import com.lifeflow.blood_donation_system.backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public void registerPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Object findByUser_userID(Integer userID) {
        return patientRepository.findByUser_userID(userID);
    }

    public void deletePatient(Patient patient) {
        patientRepository.delete(patient);
    }
}
