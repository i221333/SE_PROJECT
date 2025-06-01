package com.lifeflow.blood_donation_system.backend.repository;

import com.lifeflow.blood_donation_system.backend.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByPatientId(Integer patientId);

  @Query("SELECT r FROM Request r WHERE r.status = 'PENDING' ORDER BY r.requestType DESC, r.requestDate ASC")
  List<Map<String, Object>> findAllPendingRequests();
}