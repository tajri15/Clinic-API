package com.portofolio.clinicapi.service;

import com.portofolio.clinicapi.config.JwtUtil;
import com.portofolio.clinicapi.dto.LoginRequest;
import com.portofolio.clinicapi.dto.LoginResponse;
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
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_PATIENT);

        userRepository.save(user);
        System.out.println("User registered: " + registerRequest.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Email: " + loginRequest.getEmail());

        // Cari user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    System.out.println("ERROR: User not found: " + loginRequest.getEmail());
                    return new RuntimeException("Invalid email or password");
                });

        System.out.println("User found: " + user.getEmail());
        System.out.println("DB Password (hashed): " + user.getPassword());
        System.out.println("Input password: " + loginRequest.getPassword());

        // Check password manually
        boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        System.out.println("Password matches: " + passwordMatches);

        if (!passwordMatches) {
            System.out.println("ERROR: Password doesn't match");
            throw new RuntimeException("Invalid email or password");
        }

        try {
            // Generate token langsung
            String token = jwtUtil.generateToken(user);
            System.out.println("Token generated successfully");
            System.out.println("Token: " + token);

            return new LoginResponse(token);

        } catch (Exception e) {
            System.out.println("ERROR generating token: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
}