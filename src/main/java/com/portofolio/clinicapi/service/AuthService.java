package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.LoginRequest;
import com.portofolio.clinicapi.dto.LoginResponse;
import com.portofolio.clinicapi.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}