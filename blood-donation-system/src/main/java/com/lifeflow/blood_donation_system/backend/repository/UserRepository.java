package com.lifeflow.blood_donation_system.backend.repository;

import com.lifeflow.blood_donation_system.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}