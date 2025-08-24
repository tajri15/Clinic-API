package com.portofolio.clinicapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor // Constructor untuk semua field
public class LoginResponse {
    private String token;
}