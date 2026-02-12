package com.example.final_project.repository;

import com.example.final_project.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    List<UserNotification> findTop50ByRecipientEmailOrderByCreatedAtDesc(String recipientEmail);

    Optional<UserNotification> findByIdAndRecipientEmail(Long id, String recipientEmail);
}
