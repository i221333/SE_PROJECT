package com.lifeflow.blood_donation_system.backend.controller;

import com.lifeflow.blood_donation_system.backend.entity.Admin;
import com.lifeflow.blood_donation_system.backend.entity.Donor;
import com.lifeflow.blood_donation_system.backend.entity.Patient;
import com.lifeflow.blood_donation_system.backend.entity.User;
import com.lifeflow.blood_donation_system.backend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "Login";
    }

    @PostMapping("/process-login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {

        // Basic validation
        if (username.isBlank() || password.isBlank()) {
            redirectAttributes.addFlashAttribute("loginMessage", "All fields are required");
            return "redirect:/login";
        }

        User user = userService.authenticateUser(username, password);

        if (user == null) {
            redirectAttributes.addFlashAttribute("loginMessage", "Invalid username or password.");
            return "redirect:/login"; // Stay on the login page if authentication fails
        }

        Object roleSpecificEntity = userService.getRoleBasedEntity(user);

        // Store user details in session
        session.setAttribute("username", user.getName());
        session.setAttribute("role", user.getRole());

        switch (user.getRole()) {
            case "Admin":
                Admin admin = (Admin) userService.getRoleBasedEntity(user);
                session.setAttribute("loggedInUser", admin);
                return "redirect:/adminHome";

            case "Donor":
                Donor donor = (Donor) userService.getRoleBasedEntity(user);
                session.setAttribute("loggedInUser", donor);
                return "redirect:/donorHome";

            case "Patient":
                Patient patient = (Patient) userService.getRoleBasedEntity(user);
                session.setAttribute("loggedInUser", patient);
                return "redirect:/patientHome";

            default:
                redirectAttributes.addFlashAttribute("loginMessage", "Unknown role");
                return "redirect:/login";
        }
    }

    // Logout function to clear session data
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clears session attributes
        return "redirect:/login";
    }
}
