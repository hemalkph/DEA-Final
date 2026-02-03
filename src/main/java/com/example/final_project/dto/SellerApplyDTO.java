package com.example.final_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SellerApplyDTO {

    @NotBlank(message = "Full Name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "City or District is required")
    private String cityOrDistrict;

    private String nicOrCompanyRegNo;
}
