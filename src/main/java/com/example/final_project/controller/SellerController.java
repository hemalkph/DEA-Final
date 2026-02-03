package com.example.final_project.controller;

import com.example.final_project.dto.SellerActivateDTO;
import com.example.final_project.dto.SellerApplyDTO;
import com.example.final_project.model.SellerApplication;
import com.example.final_project.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
@RequiredArgsConstructor
@Slf4j
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/apply")
    public ResponseEntity<?> apply(@Valid @RequestBody SellerApplyDTO dto) {
        log.info("Received seller application from: {}", dto.getEmail());
        SellerApplication saved = sellerService.apply(dto);
        log.info("Saved seller application with ID: {}, status: {}", saved.getId(), saved.getStatus());
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateAccount(@Valid @RequestBody SellerActivateDTO dto) {
        sellerService.activateAccount(dto);
        return ResponseEntity.ok("Account activated successfully");
    }
}
