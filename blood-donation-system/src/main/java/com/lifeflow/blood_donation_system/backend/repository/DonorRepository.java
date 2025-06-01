package com.lifeflow.blood_donation_system.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lifeflow.blood_donation_system.backend.entity.Donor;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Integer> {
    Object findByUser_userID(Integer userID);
}

