package com.lifeflow.blood_donation_system.backend.controller;

import com.lifeflow.blood_donation_system.backend.entity.Appointment;
import com.lifeflow.blood_donation_system.backend.entity.Donor;
import com.lifeflow.blood_donation_system.backend.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DonorHomeController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/donorHome")
    public String redirectToDonorHtml(HttpSession session, Model model) {
        Object userObj = session.getAttribute("loggedInUser");

        if (userObj == null) {
            return "redirect:/login";
        }

        if (!(userObj instanceof Donor donor)) {
            return "redirect:/login";
        }

        model.addAttribute("username", session.getAttribute("username"));

        List<Appointment> appointmentHistory = appointmentService.getAppointmentsByDonor(donor.getId());

        if (appointmentHistory.isEmpty()) {
            model.addAttribute("historyMessage", "No appointment history available.");
        } else {
            model.addAttribute("appointments", appointmentHistory);
        }

        return "DonorHome";
    }

    // Schedule appointment for the logged-in donor
    @PostMapping("/donorHome/schedule")
    public String scheduleAppointment(@RequestParam String date,
                                      @RequestParam String time,
                                      HttpSession session) {
        Donor donor = (Donor) session.getAttribute("loggedInUser");
        if (donor == null) {
            return "redirect:/login"; // Redirect unauthorized users
        }

        LocalDateTime appointmentDateTime = LocalDateTime.parse(date + "T" + time);

        Appointment appointment = new Appointment();
        appointment.setDonor(donor);
        appointment.setAppointmentDate(appointmentDateTime);
        appointment.setStatus(Appointment.AppointmentStatus.SCHEDULED);

        appointmentService.scheduleAppointment(appointment);

        return "redirect:/donorHome"; // Reload dashboard
    }

    // View appointment history
    @GetMapping("/donorHome/history")
    public String viewHistory(Model model, HttpSession session) {
        Donor donor = (Donor) session.getAttribute("loggedInUser");

        if (donor == null) {
            return "redirect:/login"; // Redirect unauthorized users
        }

        List<Appointment> appointmentHistory = appointmentService.getAppointmentsByDonor(donor.getId());

        if (appointmentHistory.isEmpty()) {
            model.addAttribute("historyMessage", "No appointment history available.");
        } else {
            model.addAttribute("appointments", appointmentHistory);
        }

        return "DonorHome"; // Load history panel within DonorHome
    }
}
