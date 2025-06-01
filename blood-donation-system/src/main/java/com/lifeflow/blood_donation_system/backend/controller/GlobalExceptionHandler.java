package com.lifeflow.blood_donation_system.backend.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDatabaseErrors(DataIntegrityViolationException ex, Model model, HttpServletRequest request) {
        model.addAttribute("errorMessage", "Database integrity error: " + ex.getMessage());
        return getPreviousPage(request); // Use helper method to get previous page
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationErrors(MethodArgumentNotValidException ex, Model model, HttpServletRequest request) {
        FieldError error = ex.getBindingResult().getFieldError();
        model.addAttribute("errorMessage", "Validation failed: " + (error != null ? error.getDefaultMessage() : "Invalid input"));
        return getPreviousPage(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model, HttpServletRequest request) {
        model.addAttribute("errorMessage", "Error: " + ex.getMessage());
        return getPreviousPage(request);
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model, HttpServletRequest request){
        model.addAttribute("errorMessage", "A runtime error occurred: " + ex.getMessage());
        return getPreviousPage(request);
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericErrors(Exception ex, Model model, HttpServletRequest request) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        return getPreviousPage(request);
    }

    private String getPreviousPage(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        String[] parts = referrer.split("/");
        return parts[parts.length - 1];
    }
}