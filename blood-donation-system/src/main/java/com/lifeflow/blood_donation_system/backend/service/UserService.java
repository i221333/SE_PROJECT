package com.lifeflow.blood_donation_system.backend.service;

import com.lifeflow.blood_donation_system.backend.entity.Donor;
import com.lifeflow.blood_donation_system.backend.entity.Patient;
import com.lifeflow.blood_donation_system.backend.entity.User;
import com.lifeflow.blood_donation_system.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AdminService adminService;
    private final DonorService donorService;
    private final PatientService patientService;

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Successful authentication
        }
        return null; // Failed authentication
    }

    public Object getRoleBasedEntity(User user) {
        return switch (user.getRole()) {
            case "Admin" -> adminService.findByUser_userID(user.getUserID());
            case "Donor" -> donorService.findByUser_userID(user.getUserID());
            case "Patient" -> patientService.findByUser_userID(user.getUserID());
            default -> null;
        };
    }

    public boolean registerUser(String name, String email, String password, String role, String bloodGroup) {
        switch (role) {
            case "Donor":
                Donor donor = new Donor(name, email, password, bloodGroup);
                donorService.registerDonor(donor);
                break;
            case "Patient":
                Patient patient = new Patient(name, email, password, bloodGroup);
                patientService.registerPatient(patient);
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean deleteUser(int userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        Object userObj = getRoleBasedEntity(user);

        switch (user.getRole()) {
            case "Donor":
                donorService.deleteDonor((Donor) userObj);
                break;
            case "Patient":
                patientService.deletePatient((Patient) userObj);
                break;
            default:
                return false;
        }

        return true;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

