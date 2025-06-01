package com.lifeflow.blood_donation_system.backend.service;

import com.lifeflow.blood_donation_system.backend.entity.Donor;
import com.lifeflow.blood_donation_system.backend.repository.DonorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonorService {

    private final DonorRepository donorRepository;

    public void registerDonor(Donor donor) {
        donorRepository.save(donor);
    }

    public Object findByUser_userID(Integer userID) {
        return donorRepository.findByUser_userID(userID);
    }

    public void deleteDonor(Donor donor) {
        donorRepository.delete(donor);
    }
}
