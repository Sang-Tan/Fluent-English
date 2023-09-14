package com.fluentenglish.web.admin.authen.repository;

import com.fluentenglish.web.admin.authen.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Optional<Admin> findByFullName(String fullName);
    public Optional<Admin> findByEmail(String email);
}
