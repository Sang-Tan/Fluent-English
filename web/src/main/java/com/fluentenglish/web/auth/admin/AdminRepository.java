package com.fluentenglish.web.auth.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByFullName(String fullName);
    Optional<Admin> findByEmail(String email);
}
