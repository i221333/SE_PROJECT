package com.lifeflow.blood_donation_system.backend.controller;

import com.lifeflow.blood_donation_system.backend.entity.*;
import com.lifeflow.blood_donation_system.backend.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/adminHome")
public class AdminHomeController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private UserService userService;

    @Autowired
    RequestService requestService;

    @GetMapping("")
    public String redirectToAdminHtml(HttpSession session, Model model) {
        Object userObj = session.getAttribute("loggedInUser");

        if (userObj == null) {
            return "redirect:/login";
        }

        if (!(userObj instanceof Admin admin)) {
            return "redirect:/login";
        }

        model.addAttribute("username", session.getAttribute("username"));

        return "AdminHome";
    }

    @PostMapping("/add-user")
    @ResponseBody
    public ResponseEntity<String> addUser(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String email = payload.get("email");
        String password = payload.get("password");
        String role = payload.get("role");
        String bloodGroup = payload.get("bloodGroup");

        boolean success = userService.registerUser(name, email, password, role, bloodGroup);
        return success
                ? ResponseEntity.ok("User added successfully.")
                : ResponseEntity.badRequest().body("Failed to add user.");
    }

    @PostMapping("/delete-user")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@RequestParam("userId") int userId) {
        boolean success = userService.deleteUser(userId);
        return success
                ? ResponseEntity.ok("User deleted successfully.")
                : ResponseEntity.badRequest().body("Failed to delete user.");
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/requests")
    @ResponseBody
    public List<Map<String, Object>> getAllRequests() {
        String sql = "SELECT requestID, requestDate, patientID, quantityNeeded, status, bloodGroup FROM request";
        return jdbcTemplate.queryForList(sql);
    }

    @PostMapping("/requests/{requestId}/accept")
    @ResponseBody
    public ResponseEntity<String> acceptRequest(@PathVariable int requestId) {
        boolean success = requestService.acceptRequest(requestId);
        return success
                ? ResponseEntity.ok("Request accepted.")
                : ResponseEntity.badRequest().body("Failed to accept request.");
    }

    @PostMapping("/requests/{requestId}/decline")
    @ResponseBody
    public ResponseEntity<String> declineRequest(@PathVariable int requestId) {
        boolean success = requestService.declineRequest(requestId);
        return success
                ? ResponseEntity.ok("Request declined.")
                : ResponseEntity.badRequest().body("Failed to decline request.");
    }

    @GetMapping("/inventory")
    @ResponseBody
    public List<Inventory> listInventory() {
        return inventoryService.getAllInventory();
    }

    @PostMapping("/inventory/{bloodGroup}")
    @ResponseBody
    public ResponseEntity<String> updateInventory(
            @PathVariable String bloodGroup,
            @RequestBody Map<String, String> payload) {
        int quantity = Integer.parseInt(payload.get("quantity"));
        boolean success = updateInventory(bloodGroup, quantity);
        return success
                ? ResponseEntity.ok("Inventory updated.")
                : ResponseEntity.badRequest().body("Failed to update inventory.");
    }

    public boolean updateInventory(String inventoryId, int quantity) {
        // Step 1: Fetch current quantity
        String selectSql = "SELECT quantity FROM Inventory WHERE bloodGroup = ?";
        Integer currentQuantity = jdbcTemplate.queryForObject(selectSql, new Object[]{inventoryId}, Integer.class);

        int newQuantity = currentQuantity + quantity;

        if (newQuantity < 0) {
            return false;
        }

        String updateSql = "UPDATE Inventory SET quantity = ? WHERE bloodGroup = ?";
        int rows = jdbcTemplate.update(updateSql, newQuantity, inventoryId);

        return rows > 0;
    }
}