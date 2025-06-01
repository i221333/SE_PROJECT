package com.lifeflow.blood_donation_system.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Donor")

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donorID") // Match the column name
    private int id;

    @Column(name = "bloodGroup", nullable = false, length = 3)
    private String bloodGroup;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false, unique = true)
    private User user;

    public Donor(int userId, String name, String email, String password, int id, String bloodGroup) {
        user = new User(userId, name, email, password, "Donor");
        this.id = id;
        this.bloodGroup = bloodGroup;
    }

    public Donor(String name, String email, String password, String bloodGroup){
        user = new User(name, email, password, "Donor");
        this.bloodGroup = bloodGroup;
    }
}
