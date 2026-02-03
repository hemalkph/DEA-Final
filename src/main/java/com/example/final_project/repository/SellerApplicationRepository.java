package com.example.final_project.repository;

import com.example.final_project.model.SellerApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerApplicationRepository extends JpaRepository<SellerApplication, Long> {
    List<SellerApplication> findByStatus(SellerApplication.ApplicationStatus status);

    Optional<SellerApplication> findByEmail(String email);
}
