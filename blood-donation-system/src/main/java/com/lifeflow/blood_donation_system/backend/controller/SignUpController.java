package com.lifeflow.blood_donation_system.backend.controller;
import com.lifeflow.blood_donation_system.backend.entity.Donor;
import com.lifeflow.blood_donation_system.backend.entity.Patient;

import com.lifeflow.blood_donation_system.backend.entity.User;
import com.lifeflow.blood_donation_system.backend.service.DonorService;
import com.lifeflow.blood_donation_system.backend.service.PatientService;
import com.lifeflow.blood_donation_system.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private DonorService donorService; // Inject DonorService

    @Autowired
    private PatientService patientService; // Inject PatientService


    @GetMapping("/")
    public String redirectToSignUp() {
        return "redirect:/login";
    }

    @GetMapping("/signUp")
    public String redirectToSignUpHtml() {
        return "SignUp";
    }

    @PostMapping("/process-signup")
    public String processSignup(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String passwordConfirm,
                                @RequestParam String bloodGroup,
                                @RequestParam String role,
                                RedirectAttributes redirectAttributes
    ) {
        // Basic validation
        if (name.isBlank() || email.isBlank() || password.isBlank() || bloodGroup.isBlank()) {
            redirectAttributes.addFlashAttribute("signUpMessage", "All fields are required");
            return "redirect:/signUp";
        }
        if (!password.equals(passwordConfirm)) {
            redirectAttributes.addFlashAttribute("signUpMessage", "Passwords do not match");
            return "redirect:/signUp";
        }

        // Redirect based on role after successful signup
        switch (role) {
            case "Donor":
                Donor donor = new Donor(name, email, password, bloodGroup);
                donorService.registerDonor(donor);
                return "redirect:/login";
            case "Patient":
                Patient patient = new Patient(name, email, password, bloodGroup);
                patientService.registerPatient(patient);
                return "redirect:/login";
            default:
                redirectAttributes.addFlashAttribute("signUpMessage", "Unknown role assigned");
                return "redirect:/signUp";
        }
    }
}