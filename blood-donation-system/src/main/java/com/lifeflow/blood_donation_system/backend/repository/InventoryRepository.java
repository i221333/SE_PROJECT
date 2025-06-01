package com.lifeflow.blood_donation_system.backend.repository;

import com.lifeflow.blood_donation_system.backend.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {}
