package com.lifeflow.blood_donation_system.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Inventory")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @Column(name = "bloodGroup", nullable = false, length = 3)
    private String bloodGroup;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
