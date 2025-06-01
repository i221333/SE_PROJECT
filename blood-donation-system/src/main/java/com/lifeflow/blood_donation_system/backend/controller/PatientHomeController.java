package com.lifeflow.blood_donation_system.backend.controller;

import com.lifeflow.blood_donation_system.backend.entity.Donor;
import com.lifeflow.blood_donation_system.backend.entity.Patient;
import com.lifeflow.blood_donation_system.backend.entity.Request;
import com.lifeflow.blood_donation_system.backend.service.RequestService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
public class PatientHomeController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/patientHome")
    public String redirectToPatientHtml(HttpSession session, Model model) {
        Object userObj = session.getAttribute("loggedInUser");

        if (userObj == null) {
            return "redirect:/login";
        }

        if (!(userObj instanceof Patient patient)) {
            return "redirect:/login";
        }

        model.addAttribute("username", session.getAttribute("username"));

        List<Request> requestHistory = requestService.getRequestsByPatient(patient.getId());

        if (requestHistory.isEmpty()) {
            model.addAttribute("errorMessage", "No blood request history available.");
        } else {
            model.addAttribute("requests", requestHistory);
        }

        return "PatientHome";
    }

    // Submit a blood request (scheduled or urgent)
    @PostMapping("/patientHome/request-blood")
    public String submitBloodRequest(
            @RequestParam String bloodGroup,
            @RequestParam Integer quantity, // Corrected to match HTML name
            @RequestParam(required = false) String scheduledDate, // Corrected name
            @RequestParam String reason,
            HttpSession session,
            Model model) {

        Patient patient = (Patient) session.getAttribute("loggedInUser");
        if (patient == null) {
            return "redirect:/login";
        }

        Request request = new Request();
        request.setPatient(patient);
        request.setBloodGroup(bloodGroup);
        request.setQuantityNeeded(quantity);
        request.setReason(reason);

        if (scheduledDate != null && !scheduledDate.isEmpty()) {
            // It's a scheduled request
            try {
                LocalDateTime scheduledDateTime = LocalDateTime.parse(scheduledDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                request.setRequestDate(scheduledDateTime);
                request.setRequestType(Request.RequestType.SCHEDULED); // Set request type
            } catch (DateTimeParseException e) {
                model.addAttribute("message", "Invalid date/time format. Please use the correct format."); //send message to thymeleaf
                return "redirect:/patientHome"; // Stay on the same page
            }
        } else {
            // It's an urgent request
            request.setRequestDate(LocalDateTime.now());
            request.setRequestType(Request.RequestType.URGENT);     // Set request type
        }

        request.setStatus(Request.RequestStatus.PENDING);
        requestService.scheduleRequest(request); // Use a general saveRequest

        model.addAttribute("message", "Blood request submitted successfully!"); //send message to thymeleaf
        return "redirect:/patientHome"; // Reload dashboard
    }

    // View all blood requests by the patient
    @GetMapping("/patientHome/view-requests")
    public String viewBloodRequests(Model model, HttpSession session) {
        Patient patient = (Patient) session.getAttribute("loggedInUser");
        if (patient == null) {
            return "redirect:/login"; // Redirect unauthorized users
        }

        List<Request> requestHistory = requestService.getRequestsByPatient(patient.getId());

        if (requestHistory.isEmpty()) {
            model.addAttribute("errorMessage", "No blood request history available.");
        } else {
            model.addAttribute("requests", requestHistory);
        }

        return "PatientHome"; // Load history panel within PatientHome
    }
}