package com.fluentenglish.web.auth.admin;

import com.fluentenglish.web.auth.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminDetailsService implements UserDetailsService {
    @Autowired
    AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin Not Found with email: " + email));
            return AdminDetails.builder()
                    .id(admin.getId())
                    .email(admin.getEmail())
                    .password(admin.getPassword())
                    .enabled(admin.isEnabled())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN)))
                    .build();
    }
}
