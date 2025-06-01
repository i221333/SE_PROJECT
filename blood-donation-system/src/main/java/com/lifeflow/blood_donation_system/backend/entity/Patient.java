package com.lifeflow.blood_donation_system.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Patient")

@NoArgsConstructor
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patientID")
    private int id;

    @Column(name = "bloodGroup", nullable = false, length = 3)
    private String bloodGroup;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false, unique = true)
    private User user;

    public Patient(int userId, String name, String email, String password, int id, String bloodGroup) {
        user = new User(userId, name, email, password, "Patient");
        this.id = id;
        this.bloodGroup = bloodGroup;
    }

    public Patient(String name, String email, String password, String bloodGroup){
        user = new User(name, email, password, "Patient");
        this.bloodGroup = bloodGroup;
    }

    public boolean registerPatient() {
        String userInsertQuery = "INSERT INTO users (Name, Email, password, role) VALUES (?, ?, ?, ?)";
        String getUserIdQuery = "SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1";
        String patientInsertQuery = "INSERT INTO lifeflow.patient (blood_group, user_id) VALUES (?, ?)";

        /*try {
            // Load the MySQL driver explicitly
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 PreparedStatement userInsertStmt = connection.prepareStatement(userInsertQuery);
                 PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdQuery);
                 PreparedStatement patientInsertStmt = connection.prepareStatement(patientInsertQuery)) {

                // Insert user details
                userInsertStmt.setString(1, this.name);
                userInsertStmt.setString(2, this.email);
                userInsertStmt.setString(3, this.password);
                userInsertStmt.setString(4, "patient");
                int rowsInserted = userInsertStmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("User details inserted successfully.");
                } else {
                    System.out.println("User details insertion failed.");
                    return false;
                }

                // Fetch the latest user_id
                ResultSet rs = getUserIdStmt.executeQuery();
                if (rs.next()) {
                    int userID = rs.getInt("user_id");
                    System.out.println("Fetched user ID: " + userID);

                    // Insert patient details
                    patientInsertStmt.setString(1, this.bloodGroup);
                    patientInsertStmt.setInt(2, userID);
                    int patientRowsInserted = patientInsertStmt.executeUpdate();

                    if (patientRowsInserted > 0) {
                        System.out.println("Patient details inserted successfully.");
                        return true;
                    } else {
                        System.out.println("Patient details insertion failed.");
                        return false;
                    }
                } else {
                    System.out.println("Failed to fetch user ID.");
                    return false;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.out.println("Error during patient registration: " + e.getMessage());
            e.printStackTrace();
            return false;
        }*/
        System.out.println("Patient successfully registered");
        return true;
    }
}

