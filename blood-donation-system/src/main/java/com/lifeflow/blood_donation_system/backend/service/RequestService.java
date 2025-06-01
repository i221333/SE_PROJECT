package com.lifeflow.blood_donation_system.backend.service;

import com.lifeflow.blood_donation_system.backend.entity.Request;
import com.lifeflow.blood_donation_system.backend.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    private final InventoryService inventoryService;

    // Save a blood request
    public void scheduleRequest(Request request) {
        requestRepository.save(request);
    }

    // Get all requests made by a specific patient
    public List<Request> getRequestsByPatient(Integer patientId) {
        return requestRepository.findByPatientId(patientId);
    }

    public List<Map<String, Object>> getPendingRequests() {
        return requestRepository.findAllPendingRequests();
    }

    public boolean acceptRequest(int requestId) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isEmpty()) return false;

        Request request = optionalRequest.get();

        if(inventoryService.updateInventory(request)){
            request.setStatus(Request.RequestStatus.APPROVED);
            requestRepository.save(request);
        }
        return true;
    }

    public boolean declineRequest(int requestId) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isEmpty()) return false;

        Request request = optionalRequest.get();

        request.setStatus(Request.RequestStatus.REJECTED);
        requestRepository.save(request);
        return true;
    }
}
