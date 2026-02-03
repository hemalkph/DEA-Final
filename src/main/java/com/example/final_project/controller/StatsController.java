package com.example.final_project.controller;

import com.example.final_project.dto.StatsDTO;
import com.example.final_project.model.PropertyStatus;
import com.example.final_project.repository.AgentRepository;
import com.example.final_project.repository.PropertyRepository;
import com.example.final_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/stats")
@RequiredArgsConstructor
public class StatsController {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final AgentRepository agentRepository;

    @GetMapping
    public ResponseEntity<StatsDTO> getStats() {
        long totalUsers = userRepository.count();
        long totalProperties = propertyRepository.count();
        long activeListings = propertyRepository.countByStatus(PropertyStatus.AVAILABLE);
        long pendingProperties = propertyRepository.countByStatus(PropertyStatus.PENDING);
        long soldProperties = propertyRepository.countByStatus(PropertyStatus.SOLD);
        long rentedProperties = propertyRepository.countByStatus(PropertyStatus.RENTED);
        long totalAgents = agentRepository.count();

        StatsDTO stats = new StatsDTO(
                totalUsers,
                totalProperties,
                activeListings,
                pendingProperties,
                soldProperties,
                rentedProperties,
                totalAgents);

        return ResponseEntity.ok(stats);
    }
}
