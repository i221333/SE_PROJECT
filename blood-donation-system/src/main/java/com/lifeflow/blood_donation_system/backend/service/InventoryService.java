package com.lifeflow.blood_donation_system.backend.service;

import com.lifeflow.blood_donation_system.backend.entity.Inventory;
import com.lifeflow.blood_donation_system.backend.entity.Request;
import com.lifeflow.blood_donation_system.backend.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean updateInventory(Request request) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(request.getBloodGroup());
        if (optionalInventory.isEmpty()) return false;

        Inventory inventory = optionalInventory.get();

        if (inventory.getQuantity() < request.getQuantityNeeded()) {
            return false; // Not enough inventory
        }

        // Deduct from inventory and update status
        inventory.setQuantity(inventory.getQuantity() - request.getQuantityNeeded());
        inventoryRepository.save(inventory);

        return true;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public boolean updateInventory(String bloodGroup, int quantity) {
        return true;
    }

    public Inventory getInventory(String inventoryId) {
        return inventoryRepository.findById(inventoryId).orElseThrow();
    }
}
