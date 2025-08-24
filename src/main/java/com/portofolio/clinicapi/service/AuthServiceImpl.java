package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.dto.RegisterRequest;
import com.portofolio.clinicapi.model.Role;
import com.portofolio.clinicapi.model.User;
import com.portofolio.clinicapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Dependency Injection melalui constructor
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        // Cek apakah email sudah terdaftar
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        // Enkripsi password sebelum disimpan
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        // User baru otomatis memiliki role sebagai PASIEN
        user.setRole(Role.ROLE_PATIENT);

        userRepository.save(user);
    }
}