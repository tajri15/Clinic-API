package com.portofolio.clinicapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; // Import UserDetails

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
// TAMBAHKAN IMPLEMENTS UserDetails DI SINI
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    // ==================================================================
    // METHOD-METHOD BARU YANG DIBUTUHKAN OLEH UserDetails
    // ==================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Method ini mengembalikan role user
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // Spring Security akan menggunakan email sebagai "username"
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Untuk simpelnya, kita set true (akun tidak pernah expired)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Akun tidak pernah terkunci
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Kredensial (password) tidak pernah expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Akun selalu aktif
        return true;
    }
}