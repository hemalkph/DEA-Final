package com.example.final_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String address;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PropertyType type; // SALE or RENT

    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    private String imageUrl; // Main thumbnail

    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_url")
    @Builder.Default
    private java.util.List<String> imageUrls = new java.util.ArrayList<>();

    private Integer bedrooms;
    private Integer bathrooms;
    private Double areaSqFt;

    @ElementCollection
    @CollectionTable(name = "property_facilities", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "facility")
    @Builder.Default
    private java.util.List<String> facilities = new java.util.ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String houseRules;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private User agent;

    @ManyToOne
    @JoinColumn(name = "assigned_agent_id")
    private Agent assignedAgent;

    // Seller contact information for listing submissions
    private String ownerName;
    private String ownerPhone;
    private String ownerEmail;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private java.util.List<PropertyMedia> mediaFiles = new java.util.ArrayList<>();

    private String rejectionReason; // Admin's reason for rejecting submission

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
