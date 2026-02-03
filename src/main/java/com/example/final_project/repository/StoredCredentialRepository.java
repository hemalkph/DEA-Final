package com.example.final_project.repository;

import com.example.final_project.model.StoredCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoredCredentialRepository extends JpaRepository<StoredCredential, Long> {
    Optional<StoredCredential> findByUsername(String username);
}
