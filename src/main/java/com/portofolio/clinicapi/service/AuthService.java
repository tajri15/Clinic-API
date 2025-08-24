package com.portofolio.clinicapi.service;

// IMPORT YANG DIPERLUKAN ADA DI SINI
import com.portofolio.clinicapi.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest registerRequest);
}