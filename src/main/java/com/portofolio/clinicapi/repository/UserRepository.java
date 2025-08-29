package com.portofolio.clinicapi.repository;

import com.portofolio.clinicapi.model.Role;
import com.portofolio.clinicapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // METHOD BARU: Untuk menghitung jumlah user berdasarkan role
    long countByRole(Role role);
}