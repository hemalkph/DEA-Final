package com.example.final_project.dto;

public record StatsDTO(
        long totalUsers,
        long totalProperties,
        long activeListings,
        long pendingProperties,
        long soldProperties,
        long rentedProperties,
        long totalAgents) {
}
