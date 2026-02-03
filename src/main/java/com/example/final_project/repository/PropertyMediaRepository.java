package com.example.final_project.repository;

import com.example.final_project.model.PropertyMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyMediaRepository extends JpaRepository<PropertyMedia, Long> {
}
