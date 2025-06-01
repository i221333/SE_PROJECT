package com.lifeflow.blood_donation_system.backend.repository;

import com.lifeflow.blood_donation_system.backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Object findByUser_userID(Integer userID);
}
