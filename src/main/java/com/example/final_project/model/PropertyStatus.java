package com.example.final_project.model;

public enum PropertyStatus {
    PENDING, // Awaiting admin approval
    AVAILABLE, // Approved and available for sale/rent
    SOLD, // Property has been sold
    RENTED, // Property has been rented
    REJECTED // Admin rejected the submission
}
