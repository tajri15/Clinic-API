package com.portofolio.clinicapi.controller;

// IMPORT YANG DIPERLUKAN ADA DI SINI
import com.portofolio.clinicapi.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // @Autowired private AuthService authService; // Akan digunakan nanti

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        // authService.register(request);
        return ResponseEntity.ok("User registered successfully!");
    }
}