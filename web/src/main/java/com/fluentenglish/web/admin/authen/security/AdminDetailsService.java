package com.fluentenglish.web.admin.authen.security;

import com.fluentenglish.web.admin.authen.entity.Admin;
import com.fluentenglish.web.admin.authen.repository.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("adminDetailsService")
@Transactional
//@RequiredArgsConstructor
public class AdminDetailsService implements UserDetailsService {
    @Autowired
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            Admin admin = adminRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Admin Not Found with email: " + email));
            return AdminDetails.build(admin);
    }

}
