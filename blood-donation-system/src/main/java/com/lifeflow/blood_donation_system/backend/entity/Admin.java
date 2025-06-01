package com.lifeflow.blood_donation_system.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Admin")

@NoArgsConstructor
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adminID")
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false, unique = true)
    private User user;

    public Admin(int userId, String name, String email, String password, int id) {
        user = new User(userId, name, email, password, "Admin");
        this.id = id;
    }

    public Admin(String name, String email, String password){
        user = new User(name, email, password, "Admin");
    }

}
