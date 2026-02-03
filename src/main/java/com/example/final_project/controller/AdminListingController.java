package com.example.final_project.controller;

import com.example.final_project.model.Property;
import com.example.final_project.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Admin controller for managing property listing approvals.
 * Provides endpoints to view pending listings and approve/reject them.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminListingController {

    private final PropertyService propertyService;

    /**
     * Get all properties with PENDING status.
     */
    @GetMapping("/pending")
    public ResponseEntity<List<Property>> getPendingListings() {
        List<Property> pendingProperties = propertyService.getPendingProperties();
        return ResponseEntity.ok(pendingProperties);
    }

    /**
     * Get a single listing's full details including media files.
     */
    @GetMapping("/listings/{id}")
    public ResponseEntity<?> getListingDetails(@PathVariable Long id) {
        try {
            Property property = propertyService.getPropertyById(id);
            return ResponseEntity.ok(property);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }

    /**
     * Approve a pending listing - sets status to AVAILABLE.
     */
    @PutMapping("/listings/{id}/approve")
    public ResponseEntity<?> approveListing(@PathVariable Long id) {
        try {
            Property property = propertyService.approveProperty(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Listing approved successfully.",
                    "property", property));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }

    /**
     * Reject a pending listing - sets status to REJECTED with reason.
     */
    @PutMapping("/listings/{id}/reject")
    public ResponseEntity<?> rejectListing(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String reason = body.getOrDefault("reason", "No reason provided");
            Property property = propertyService.rejectProperty(id, reason);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Listing rejected.",
                    "property", property));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }
}
