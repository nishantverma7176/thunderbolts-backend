package com.verma.loginapi.controller;

import com.verma.loginapi.dto.MedicineRequest;
import com.verma.loginapi.dto.MedicineResponse;
import com.verma.loginapi.service.MedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicine")
@CrossOrigin(origins = "*")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    // SINGLE CHAT ENDPOINT - HANDLES ALL MEDICINE QUERIES
    @PostMapping("/chat")
    public ResponseEntity<MedicineResponse> medicineChat(@RequestBody MedicineRequest request) {
        try {
            String response = medicineService.medicineChat(request.getQuery());
            return ResponseEntity.ok(MedicineResponse.success(response, "Medicine information provided"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(MedicineResponse.error("Error: " + e.getMessage()));
        }
    }

    // SINGLE TEST ENDPOINT
    @GetMapping("/test")
    public ResponseEntity<MedicineResponse> test() {
        try {
            String response = medicineService.medicineChat("What is paracetamol used for and what are common side effects?");
            return ResponseEntity.ok(MedicineResponse.success(response, "Medicine API test successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(MedicineResponse.error("Medicine API test failed: " + e.getMessage()));
        }
    }
}