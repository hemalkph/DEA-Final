package com.example.final_project.controller;

import com.example.final_project.model.Property;
import com.example.final_project.model.PropertyType;
import com.example.final_project.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PropertyController {

    private final PropertyService service;

    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(service.getAllProperties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPropertyById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchProperties(@RequestParam String q) {
        return ResponseEntity.ok(service.searchProperties(q));
    }

    /**
     * Get all listings submitted by the currently authenticated user.
     * Uses the user's email from JWT token to find matching ownerEmail.
     */
    @GetMapping("/my-listings")
    public ResponseEntity<?> getMyListings(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(java.util.Map.of(
                        "success", false,
                        "message", "Authentication required"));
            }

            // Extract user email from security context
            Object principal = org.springframework.security.core.context.SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();

            String userEmail;
            if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
                userEmail = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
            } else {
                userEmail = principal.toString();
            }

            log.info("Fetching listings for user: {}", userEmail);
            List<Property> listings = service.getPropertiesByOwnerEmail(userEmail);
            return ResponseEntity.ok(listings);
        } catch (Exception e) {
            log.error("Failed to fetch user listings: {}", e.getMessage());
            return ResponseEntity.badRequest().body(java.util.Map.of(
                    "success", false,
                    "message", "Failed to fetch listings: " + e.getMessage()));
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitProperty(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("address") String address,
            @RequestParam("price") java.math.BigDecimal price,
            @RequestParam("type") String type,
            @RequestParam(value = "bedrooms", required = false) Integer bedrooms,
            @RequestParam(value = "bathrooms", required = false) Integer bathrooms,
            @RequestParam(value = "areaSqFt", required = false) Double areaSqFt,
            @RequestParam(value = "amenities", required = false) java.util.List<String> amenities,
            @RequestParam("ownerName") String ownerName,
            @RequestParam("ownerPhone") String ownerPhone,
            @RequestParam("ownerEmail") String ownerEmail,
            @RequestParam(value = "agentName", required = false) String agentName,
            @RequestParam(value = "images", required = false) org.springframework.web.multipart.MultipartFile[] images) {

        try {
            log.info("Received property submission: title='{}', address='{}'", title, address);
            log.info("Seller info: name='{}', phone='{}', email='{}'", ownerName, ownerPhone, ownerEmail);
            log.info("Images uploaded: {}", images != null ? images.length : 0);

            // Build DTO
            com.example.final_project.dto.PropertySubmissionDTO dto = new com.example.final_project.dto.PropertySubmissionDTO();
            dto.setTitle(title);
            dto.setDescription(description);
            dto.setAddress(address);
            dto.setPrice(price);
            dto.setType(type);
            dto.setBedrooms(bedrooms);
            dto.setBathrooms(bathrooms);
            dto.setAreaSqFt(areaSqFt);
            dto.setAmenities(amenities);
            dto.setOwnerName(ownerName);
            dto.setOwnerPhone(ownerPhone);
            dto.setOwnerEmail(ownerEmail);
            dto.setAgentName(agentName);

            // Submit property
            Property property = service.submitProperty(dto, images);

            log.info("Property submitted successfully with ID: {}", property.getId());

            return ResponseEntity.ok(java.util.Map.of(
                    "success", true,
                    "message", "Property submitted successfully. Admin will review your submission.",
                    "propertyId", property.getId()));
        } catch (Exception e) {
            log.error("Failed to submit property: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(java.util.Map.of(
                    "success", false,
                    "message", "Failed to submit property: " + e.getMessage()));
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Property>> getPropertiesByType(@PathVariable PropertyType type) {
        return ResponseEntity.ok(service.getPropertiesByType(type));
    }

    @PostMapping
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        return ResponseEntity.ok(service.createProperty(property));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SELLER') or hasRole('ADMIN')")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property property) {
        return ResponseEntity.ok(service.updateProperty(id, property));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        service.deleteProperty(id);
        return ResponseEntity.ok().build();
    }
}
