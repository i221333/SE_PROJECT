package com.lifeflow.blood_donation_system.backend.service;

import com.lifeflow.blood_donation_system.backend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public Object findByUser_userID(Integer userID) {
        return adminRepository.findByUser_userID(userID);
    }
}
